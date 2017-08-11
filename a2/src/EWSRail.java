/*

The EWSRail class.  An EWSRail object has three ends.

*/

import java.awt.*;

class EWSRail extends SwitchRail {

  public EWSRail(GridLoc loc) {
    super(new Direction("east"), new Direction("west"), new Direction("south"), loc);
    setLoc(loc);
    startAngle = 90;
  }

  public EWSRail() {
    super(new Direction("east"), new Direction("west"), new Direction("south"));
    startAngle = 90;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.0;
    y1 = 0.5;
    x2 = 1.0;
    y2 = 0.5;
    x3 = 0.5;
    y3 = 0.5;
  }

  public String toString() {
    return "EWSRail";
  }

  ;
}

