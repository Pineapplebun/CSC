/*

The NWRail class.  A NWRail object has ends at the north
and west.

*/

import java.awt.*;

class NWRail extends CornerRail {

  public NWRail(GridLoc loc) {
    super(new Direction("north"), new Direction("west"), loc);
    setLoc(loc);
    startAngle = 270;
  }

  public NWRail() {
    super(new Direction("north"), new Direction("west"));
    startAngle = 270;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = -0.5;
    y1 = -0.5;
  }

  public String toString() {
    return "NWRail";
  }

  ;
}

