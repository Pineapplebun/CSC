import java.awt.*;

/**
 * The CornerRail class. A CornerRail object has two ends, which must be not be opposite
 * each other. CornerRail is a subclass of TwoEndRail.
 */
class CornerRail extends TwoEndRail {

  /**
   * The multipliers for the width and height.
   */
  private double x1, y1;

  /** The starting angle for drawing. */
  private int startAngle;

  /**
   * Create a CornerRail object with its type and location.
   * @param dir the String that is either "ne", "nw", "se", "sw".
   * @param loc the location of this CornerRail.
   */
  public CornerRail(String dir, GridLoc loc) {
    super(loc);
    checkDirection(dir);
  }

  /**
   * Create a CornerRail object with its type without a location.
   * @param dir the String that is either "ne", "nw", "se", "sw".
   */
  public CornerRail(String dir) {
    super();
    checkDirection(dir);
    color = Color.black;
  }

  /**
   * Checks which direction is specified during instantiation and sets up the variables needed
   * to define that type of CornerRail.
   * @param dir the String that is either "ne", "nw", "se", "sw".
   */
  private void checkDirection(String dir) {
    if ((dir.toLowerCase()).equals("ne")) {
      setDirection(new Direction("north"), new Direction("east"));
      x1 = 0.5;
      y1 = -0.5;
      startAngle = 180;
      direction = "ne";
    } else if ((dir.toLowerCase()).equals("nw")) {
      setDirection(new Direction("north"), new Direction("west"));
      x1 = -0.5;
      y1 = -0.5;
      startAngle = 270;
      direction = "nw";
    } else if ((dir.toLowerCase()).equals("se")) {
      setDirection(new Direction("south"), new Direction("east"));
      x1 = 0.5;
      y1 = 0.5;
      startAngle = 90;
      direction = "se";
    } else if ((dir.toLowerCase()).equals("sw")) {
      setDirection(new Direction("south"), new Direction("west"));
      x1 = -0.5;
      y1 = 0.5;
      startAngle = 0;
      direction = "sw";
    }
  }

  /**
   * Draws the CornerRail based on its color, x,y coordinates, arcAngle, startAngle, width, height.
   * @param g the graphics
   */
  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(color);
    int arcAngle = 90;
    Rectangle b = bounds();
    g.drawArc((int) (x1 * b.width), (int) (y1 * b.height), b.width, b.height, startAngle, arcAngle);
  }

  public String toString() {
    return "CornerRail";
  }

  ;
}

