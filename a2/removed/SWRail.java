/*

The SWRail class.  A SWRail object has ends at the south
and west.

*/

import java.awt.*;

class SWRail extends CornerRail {

  public SWRail(GridLoc loc) {
    super(new Direction("south"), new Direction("west"), loc);
    setLoc(loc);
    startAngle = 0;
  }

  public SWRail() {
    super(new Direction("south"), new Direction("west"));
    startAngle = 0;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = -0.5;
    y1 = 0.5;
  }

  public String toString() {
    return "SWRail";
  }

  ;
}

