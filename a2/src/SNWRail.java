/*

The SNWRail class.  An SNWRail object has three ends.

*/

import java.awt.*;

class SNWRail extends SwitchRail {

  public SNWRail(GridLoc loc) {
    super(new Direction("south"), new Direction("north"), new Direction("west"), loc);
    setLoc(loc);
    startAngle = 0;
  }

  public SNWRail() {
    super(new Direction("south"), new Direction("north"), new Direction("west"));
    startAngle = 0;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.0;
    x2 = 0.5;
    y2 = 1.0;
    x3 = -0.5;
    y3 = 0.5;
  }

  public String toString() {
    return "SNWRail";
  }

  ;
}

