/*

The WESRail class.  An WESRail object has three ends.

*/

import java.awt.*;

class WESRail extends SwitchRail {

  public WESRail(GridLoc loc) {
    super(new Direction("west"), new Direction("east"), new Direction("south"), loc);
    setLoc(loc);
    startAngle = 0;
  }

  public WESRail() {
    super(new Direction("west"), new Direction("east"), new Direction("south"));
    startAngle = 0;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.0;
    y1 = 0.5;
    x2 = 1.0;
    y2 = 0.5;
    x3 = -0.5;
    y3 = 0.5;
  }

  public String toString() {
    return "WESRail";
  }

  ;
}

