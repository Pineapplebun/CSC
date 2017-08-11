import java.awt.*;

/**
 * The Car class.  A Car object is a car in a train. It has weight and color, and can draw() and
 * move().
 */
abstract class Car {

  /** My color. */
  protected Color color;

  /** My weight, in stone. */
  int weight;

  /** The Car that immediately follows me. */
  Car nextCar;

  /** The Rail that I am currently occupying. */
  Rail currentRail;

  /** The direction in which I entered the current Rail. */
  private Direction dir;

  /** Set me moving in direction d. */
  protected void setDirection(Direction d) {
    dir = d;
  }

  /** Place this Car on Rail r. */
  protected void setRail(Rail r) {
    currentRail = r;
  }


  /** Move forward one TrackPiece; t is the current TrackPiece.  Tell
   * all of the cars I am pulling to move as well.
   */
  void move() {

    Direction nD = currentRail.exit(dir);

    Direction nextDir = nD.opposite();
    Rail nextRail = currentRail.nextRail(dir);

    // if the returned rail is the same and the direction given was the same
    // stay here
    if (nextRail != currentRail && !nextRail.occupied()) {
      dir = nextDir;
      nextRail.enter(this);
      currentRail.leave();
      currentRail = nextRail;

      // We have to call this here rather than within currentRail.enter()
      // because otherwise the wrong Rail is used...
      currentRail.repaint();

      if (nextCar != null) {
        nextCar.move();
      }
    } //else do nothing
  }

  /**
   * Returns true if the current Rail is a SwitchRail and this Car is going straight through it.
   * @return whether the switch is straight.
   */
  private boolean SwitchStraight() {
    return (currentRail instanceof SwitchRail) && ((SwitchRail) currentRail).isGoingStraight();
  }

  /** Draws the Car object based on the orientation of the Rail that it is on.
   *
   * @param g the graphics object
   */
  void draw(Graphics g) {

    GridLoc myLoc = currentRail.getLoc();
    Rectangle b = currentRail.bounds();

    // the polygon to draw on the screen.
    Polygon p;

    double width = b.width;
    double height = b.height;

    int sqrtOfHypotenuse = (int) Math.sqrt((width * width / 4.0) + (height * height / 4.0));

    int[] xPoints = new int[5];
    int[] yPoints = new int[5];
    int nPoints = 5;

    if (currentRail instanceof StraightRail
        || currentRail instanceof CrossRail
        || SwitchStraight()) {
      if (dir.equals("north") || dir.equals("south")) {
        makeStraightPolygon(xPoints, yPoints);
      } else {
        makeStraightPolygon(yPoints, xPoints);
      }
    } else if (currentRail instanceof CornerRail || currentRail instanceof SwitchRail) {
      if (currentRail.direction().equals("ne")
          || currentRail.direction().equals("nse")
          || currentRail.direction().equals("ewn")) {
        makeCornerPolygon(xPoints, yPoints, -sqrtOfHypotenuse,
            sqrtOfHypotenuse, (int) width, (int) (width / 2), (int) (height / 2), 0);
      } else if (currentRail.direction().equals("nw")
          || currentRail.direction().equals("nsw")
          || currentRail.direction().equals("wen")) {
        makeCornerPolygon(xPoints, yPoints, sqrtOfHypotenuse,
            sqrtOfHypotenuse, (int) (width / 2), 0, 0, (int) (height / 2));
      } else if (currentRail.direction().equals("se")
          || currentRail.direction().equals("sne")
          || currentRail.direction().equals("ews")) {
        makeCornerPolygon(xPoints, yPoints, -sqrtOfHypotenuse,
            -sqrtOfHypotenuse, (int) (width / 2), (int) width, (int) height, (int) (height / 2));
      } else if (currentRail.direction.equals("sw")
          || currentRail.direction().equals("snw")
          || currentRail.direction().equals("wes")) {
        makeCornerPolygon(xPoints, yPoints, sqrtOfHypotenuse,
            -sqrtOfHypotenuse, 0, (int) (width / 2), (int) (height / 2), (int) height);
      }
    }

    g.setColor(color);
    g.drawPolygon(xPoints, yPoints, 5);

  }

  /**
   * Modifies the corners of the rectangle, in order, are the back right of the car, the front right
   * of the car, the front left of the car, and the back left of the car.
   */
  private void makeCornerPolygon(int[] xPoints, int[] yPoints,
      int xSideOffset, int ySideOffset, int x0Mod, int x1Mod, int y0Mod, int y1Mod) {

    GridLoc myLoc = currentRail.getLoc();

    xPoints[0] = x0Mod;
    xPoints[1] = x1Mod;
    xPoints[2] = xPoints[1] + xSideOffset / 2;
    xPoints[3] = xPoints[0] + xSideOffset / 2;
    xPoints[4] = xPoints[0];

    yPoints[0] = y0Mod;
    yPoints[1] = y1Mod;
    yPoints[2] = yPoints[1] + ySideOffset / 2;
    yPoints[3] = yPoints[0] + ySideOffset / 2;
    yPoints[4] = yPoints[0];
  }

  /**
   * Modifies the Car to be a straight polygon.
   * @param aPoints
   * @param bPoints
   */
  private void makeStraightPolygon(int[] aPoints, int[] bPoints) {
    Rectangle b = currentRail.bounds();
    int width = b.width;
    int height = b.height;

    aPoints[0] = width / 4;
    aPoints[1] = 3 * width / 4;
    aPoints[2] = 3 * width / 4;
    aPoints[3] = width / 4;
    aPoints[4] = aPoints[0];

    bPoints[0] = height / 8;
    bPoints[1] = height / 8;
    bPoints[2] = 7 * height / 8;
    bPoints[3] = 7 * height / 8;
    bPoints[4] = bPoints[0];
  }

  public String toString() {
    return "Car";
  }

}

