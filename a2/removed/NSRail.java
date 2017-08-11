/*

The NSRail class.  A NSRail object has two ends,
which must be opposite each other.

*/

import java.awt.*;

class NSRail extends StraightRail {

  public NSRail(GridLoc loc) {
    super(new Direction("north"), new Direction("south"), loc);
    setLoc(loc);
  }

  public NSRail() {
    super(new Direction("north"), new Direction("south"));
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.0;
    x2 = 0.5;
    y2 = 1.0;
  }

  public String toString() {
    return "NSRail";
  }

  ;
}

