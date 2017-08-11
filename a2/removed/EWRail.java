/*

The EWRail class.  An EWRail object has two ends,
which must be opposite each other.

*/

import java.awt.*;

class EWRail extends StraightRail {

  public EWRail(GridLoc loc) {
    super(new Direction("east"), new Direction("west"), loc);
    setLoc(loc);
  }

  public EWRail() {
    super(new Direction("east"), new Direction("west"));
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.0;
    y1 = 0.5;
    x2 = 1.0;
    y2 = 0.5;
  }

  public String toString() {
    return "EWRail";
  }

  ;
}

