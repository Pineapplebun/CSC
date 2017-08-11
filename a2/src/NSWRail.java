/*

The NSWRail class.  An NSWRail object has three ends.

*/

import java.awt.*;

class NSWRail extends SwitchRail {

  public NSWRail(GridLoc loc) {
    super(new Direction("north"), new Direction("south"), new Direction("west"), loc);
    setLoc(loc);
    startAngle = 270;
  }

  public NSWRail() {
    super(new Direction("north"), new Direction("south"), new Direction("west"));
    startAngle = 270;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.0;
    x2 = 0.5;
    y2 = 1.0;
    x3 = -0.5;
    y3 = -0.5;
  }

  public String toString() {
    return "NSWRail";
  }

  ;
}

