import java.awt.*;

/**
 * The SwitchRail class.  A SwitchRail object has three ends, and a controller
 * which determines which ends are used.  If a Car moves on from the first end,
 * the switch determines which of the other two ends it leaves from.  If it moves
 * on from one of the other two ends, it automatically leaves by the first end.
 */
class SwitchRail extends Rail {

  /**
   * My x,y coordinates.
   */
  private double x1, y1, x2, y2, x3, y3;

  /**
   * The startAngle of a SwitchRail.
   */
  private int startAngle;

  /**
   * The ends of the directions.
   * (end1,end2) and (end1,end3) are the two pairs.
   * end1 and end2 are the straight directions (i.e., they are
   * opposite each other), and end1 and end3 form the corner.
   */
  private Direction end1, end2, end3;

  /**
   * The neighbours of this SwitchRail.
   */
  private Rail neighbour1;  // The Rail in the direction end1.
  private Rail neighbour2;  // The Rail in the direction end2.
  private Rail neighbour3;  // The Rail in the direction end3.

  /**
   * Whether I am aligned to go straight.
   */
  private boolean goingStraight;

  /**
   * Create a SwitchRail using direction type dir and GridLoc location.
   * @param dir the direction type.
   * @param loc the location type.
   */
  public SwitchRail(String dir, GridLoc loc) {
    super(loc);
    checkDirection(dir);
    color = Color.magenta;
  }

  /**
   * Create a switchRail with a direction type dir but no GridLoc location.
   * @param dir the direction type.
   */
  public SwitchRail(String dir) {
    super();
    checkDirection(dir);
    color = Color.magenta;
  }

  /**
   * Sets a Direction to each end of a switchRail.
   * @param dir1
   * @param dir2
   * @param dir3
   */
  private void setDirection(Direction dir1, Direction dir2, Direction dir3) {
    end1 = dir1;
    end2 = dir2;
    end3 = dir3;
  }

  /**
   * Checks the direction of a SwitchRail.
   * @param dir the direction type.
   */
  private void checkDirection(String dir) {
    String lowerDir = dir.toLowerCase();

    Direction north = new Direction("north");
    Direction south = new Direction("south");
    Direction east = new Direction("east");
    Direction west = new Direction("west");

    if ((lowerDir.substring(0, 2)).equals("ew")) {
      x1 = 0.0;
      y1 = 0.5;
      x2 = 1.0;
      y2 = 0.5;
      x3 = 0.5;
      if ((lowerDir.substring(2, 3)).equals("n")) {
        y3 = -0.5;
        startAngle = 180;
        setDirection(east, west, north);
        direction = "ewn";
      } else if ((lowerDir.substring(2, 3)).equals("s")) {
        y3 = 0.5;
        startAngle = 90;
        setDirection(east, west, south);
        direction = "ews";
      }
    } else if ((lowerDir.substring(0, 2)).equals("ns")) {
      x1 = 0.5;
      y1 = 0.0;
      x2 = 0.5;
      y2 = 1.0;
      y3 = -0.5;
      if ((lowerDir.substring(2, 3)).equals("e")) {
        x3 = 0.5;
        startAngle = 180;
        setDirection(north, south, east);
        direction = "nse";
      } else if ((lowerDir.substring(2, 3)).equals("w")) {
        x3 = -0.5;
        startAngle = 270;
        setDirection(north, south, west);
        direction = "nsw";
      }
    } else if ((lowerDir.substring(0, 2)).equals("sn")) {
      x1 = 0.5;
      y1 = 0.0;
      x2 = 0.5;
      y2 = 1.0;
      y3 = 0.5;
      if ((lowerDir.substring(2, 3)).equals("e")) {
        x3 = 0.5;
        startAngle = 90;
        setDirection(south, north, east);
        direction = "sne";
      } else if ((lowerDir.substring(2, 3)).equals("w")) {
        x3 = -0.5;
        startAngle = 0;
        setDirection(south, north, west);
        direction = "snw";
      }
    } else if ((lowerDir.substring(0, 2)).equals("we")) {
      x1 = 0.0;
      y1 = 0.5;
      x2 = 1.0;
      y2 = 0.5;
      x3 = -0.5;
      if ((lowerDir.substring(2, 3)).equals("n")) {
        y3 = -0.5;
        startAngle = 270;
        setDirection(west, east, north);
        direction = "wen";
      } else if ((lowerDir.substring(2, 3)).equals("s")) {
        y3 = 0.5;
        startAngle = 0;
        setDirection(west, east, south);
        direction = "wes";
      }
    }
  }

  /**
   * Return true if the exit exists for a Direction d.
   * @param d a Direction.
   * @return true if the exit exists.
   */
  public boolean exitOK(Direction d) {
    return d.equals(end1) || d.equals(end2) || d.equals(end3);
  }

  /**
   * Register that r is adjacent to me from direction d.
   * @param r the Rail that is to register a Direction d.
   * @param d the Direction to be registered.
   */
  public void register(Rail r, Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = r;
      } else if (d.equals(end2)) {
        neighbour2 = r;
      } else {
        neighbour3 = r;
      }
    }
  }

  /**
   * Unregister from this direction.
   * @param d the Direction to unregister from.
   */
  public void unRegister(Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = null;
      } else if (d.equals(end2)) {
        neighbour2 = null;
      } else {
        neighbour3 = null;
      }
    }
  }

  /**
   * Given that d is the Direction from which a Car entered, report where the Car will exit.
   * Note that if d is not end1's Direction, then it will have to exit toward end1.
   *
   * @param d the direction from which a Car entered.
   */
  public Direction exit(Direction d) {
    if (validDir(d)) {
      if (goingStraight) {
        if (d.equals(end1) || d.equals(end2)) {
          return d.equals(end1) ? end2 : end1;
        }
      } else {
        if (d.equals(end1) || d.equals(end3)) {
          return d.equals(end3) ? end1 : end3; //if coming in from end3 , go to end1
        }
      }
    }
    return d; //not straight and coming in from direction opposite to end3 and end1
  }

  /**
   * Returns the nextRail.
   * d is the direction that I entered from of the current rail,
   * and must be one of end1, end2 and end3. Return the Rail at the other end.
   * @param d the direction
   */
  protected Rail nextRail(Direction d) {
    if (validDir(d)) {
      Direction nD = this.exit(d).opposite(); //the direction the car is coming from for nextRail
      if (goingStraight) {
        return d.equals(end1) ? neighbour2 : neighbour1;
      } else if (d.equals(end1)) {//(!nD.equals(end2) && !nD.equals(end3))
        if ((neighbour3.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour3;
        }
      } else if (d.equals(end3)) {
        if ((neighbour1.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour1;//d.equals(end3) ? neighbour1 : neighbour3;
        }
      }
    } else { //not possible
      return this;
    }
    return this;
  }

  /**
   * Handle a mouse click.  This will toggle the direction of the switch.
   * @param evt
   * @return
   */
  public boolean handleEvent(Event evt) {
    Object target = evt.target;

    if (evt.id == Event.MOUSE_DOWN && !occupied()) {
      goingStraight = !goingStraight;

      repaint();
      return true;
    }

    // If we get this far, I couldn't handle the event
    return false;
  }

  /**
   * Return true if your are going straight.
   * @return true if are you going straight.
   */
  boolean isGoingStraight() {
    return goingStraight;
  }

  /**
   * Return the direction.
   * @return the direction.
   */
  protected String direction() {
    return direction;
  }

  /**
   * Draws the SwitchRail.
   * @param g the graphics.
   */
  public void draw(Graphics g) {
    int arcAngle = 90;
    super.draw(g);

    Rectangle b = bounds();

    // Draw current direction of the switch darker.
    if (goingStraight) {
      g.setColor(Color.lightGray);
      g.drawArc((int) (x3 * b.width), (int) (y3 * b.height),
          b.width, b.height, startAngle, arcAngle);
      g.setColor(color);
      g.drawLine((int) (x1 * b.width), (int) (y1 * b.height),
          (int) (x2 * b.width), (int) (y2 * b.height));
    } else {
      g.setColor(Color.lightGray);
      g.drawLine((int) (x1 * b.width), (int) (y1 * b.height),
          (int) (x2 * b.width), (int) (y2 * b.height));
      g.setColor(color);
      g.drawArc((int) (x3 * b.width), (int) (y3 * b.height),
          b.width, b.height, startAngle, arcAngle);
    }
  }

  public String toString() {
    return "SwitchRail";
  }

  ;
}

