/*

The WENRail class.  An WENRail object has three ends.

*/

import java.awt.*;

class WENRail extends SwitchRail {

  public WENRail(GridLoc loc) {
    super(new Direction("west"), new Direction("east"), new Direction("north"), loc);
    setLoc(loc);
    startAngle = 270;
  }

  public WENRail() {
    super(new Direction("west"), new Direction("east"), new Direction("north"));
    startAngle = 270;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.0;
    y1 = 0.5;
    x2 = 1.0;
    y2 = 0.5;
    x3 = -0.5;
    y3 = -0.5;
  }

  public String toString() {
    return "WENRail";
  }

  ;
}

