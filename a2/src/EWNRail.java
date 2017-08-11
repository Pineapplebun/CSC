/*

The EWNRail class.  An EWNRail object has three ends.

*/

import java.awt.*;

class EWNRail extends SwitchRail {

  public EWNRail(GridLoc loc) {
    super(new Direction("east"), new Direction("west"), new Direction("north"), loc);
    setLoc(loc);
    startAngle = 180;
  }

  public EWNRail() {
    super(new Direction("east"), new Direction("west"), new Direction("north"));
    startAngle = 180;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.0;
    y1 = 0.5;
    x2 = 1.0;
    y2 = 0.5;
    x3 = 0.5;
    y3 = -0.5;
  }

  public String toString() {
    return "EWNRail";
  }

  ;
}

