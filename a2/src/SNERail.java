/*

The SNERail class.  An SNERail object has two ends,
which must be opposite each other.

*/

import java.awt.*;

class SNERail extends SwitchRail {

  public SNERail(GridLoc loc) {
    super(new Direction("south"), new Direction("north"), new Direction("east"), loc);
    setLoc(loc);
    startAngle = 90;
  }

  public SNERail() {
    super(new Direction("south"), new Direction("north"), new Direction("east"));
    startAngle = 90;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.0;
    x2 = 0.5;
    y2 = 1.0;
    x3 = 0.5;
    y3 = 0.5;
  }

  public String toString() {
    return "SNERail";
  }

  ;

}

