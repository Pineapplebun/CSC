import java.awt.*;

/**
 * The StraightRail class.
 * A StraightRail object is blue and has two ends, which must be opposite each other.
 */
class StraightRail extends TwoEndRail {

  /**
   * The multipliers for the endpoints of the StraightRail.
   */
  private double x1, y1, x2, y2;

  /**
   * Create a StraightRail using the direction type dir and a GridLoc location.
   * @param dir the direction type.
   * @param loc the location of the StraightRail.
   */
  public StraightRail(String dir, GridLoc loc) {
    super(loc);
    color = Color.blue;
    checkDirection(dir);
  }

  /**
   * Create a StraightRail using only the direction type.
   * @param dir the direction type.
   */
  public StraightRail(String dir) {
    super();
    color = Color.blue;
    checkDirection(dir);
  }

  /**
   * Checks the direction type and sets the x,y variables and Direction values.
   * @param dir the direction type.
   */
  private void checkDirection(String dir) {
    if ((dir.toLowerCase()).equals("ew")) {
      setDirection(new Direction("east"), new Direction("west"));
      x1 = 0.0;
      y1 = 0.5;
      x2 = 1.0;
      y2 = 0.5;
      direction = "ew";
    } else if ((dir.toLowerCase()).equals("ns")) {
      setDirection(new Direction("north"), new Direction("south"));
      x1 = 0.5;
      y1 = 0.0;
      x2 = 0.5;
      y2 = 1.0;
      direction = "ns";
    }
  }

  /**
   * Draws a StraightRail.
   * @param g the graphics.
   */
  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(color);
    Rectangle b = bounds();
    g.drawLine((int) (x1 * b.width), (int) (y1 * b.height),
        (int) (x2 * b.width), (int) (y2 * b.height));
  }

  public String toString() {
    return "StraightRail";
  }
}

