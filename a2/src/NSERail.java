/*

The NSERail class.  An NSERail object has three exits.

*/

import java.awt.*;

class NSERail extends SwitchRail {

  public NSERail(GridLoc loc) {
    super(new Direction("north"), new Direction("south"), new Direction("east"), loc);
    setLoc(loc);
    startAngle = 180;
  }

  public NSERail() {
    super(new Direction("north"), new Direction("south"), new Direction("east"));
    startAngle = 180;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.0;
    x2 = 0.5;
    y2 = 1.0;
    x3 = 0.5;
    y3 = -0.5;
  }

  public String toString() {
    return "NSERail";
  }

  ;
}

