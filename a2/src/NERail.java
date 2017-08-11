/*

The NERail class.  A NERail object has ends at the north
and east.

*/

import java.awt.*;

class NERail extends CornerRail {

  public NERail(GridLoc loc) {
    super(new Direction("north"), new Direction("east"), loc);
    setLoc(loc);
    startAngle = 180;
  }

  public NERail() {
    super(new Direction("north"), new Direction("east"));
    startAngle = 180;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = -0.5;
  }

  public String toString() {
    return "NERail";
  }

  ;
}

