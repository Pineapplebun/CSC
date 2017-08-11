/*

The StraightRail class.  A StraightRail object has two ends,
which must be opposite each other.

*/

import java.awt.*;

class StraightRail extends TwoEndRail {

  // The multipliers for the endpoints of the StraightRail.
  double x1, y1, x2, y2;

  public StraightRail(GridLoc loc, String type) {

    if (type.toLowerCase().equals("NS")) {
      super(new Direction("north"), new Direction("south"), loc);
      x1 = 0.5;
      y1 = 0.0;
      x2 = 0.5;
      y2 = 1.0;

    }
    else if (type.toLowerCase().equals("EW")) {
      x1 = 0.0;
      y1 = 0.5;
      x2 = 1.0;
      y2 = 0.5;
    }

    color = Color.blue;
  }

  public StraightRail(Direction e1, Direction e2, String type) {
    super(e1, e2);
    color = Color.blue;
  }

  // Redraw myself.
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

  ;
}

