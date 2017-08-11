/*

The SERail class.  A SERail object has ends at the south
and east.

*/

import java.awt.*;

class SERail extends CornerRail {

  public SERail(GridLoc loc) {
    super(new Direction("south"), new Direction("east"), loc);
    setLoc(loc);
    startAngle = 90;
  }

  public SERail() {
    super(new Direction("south"), new Direction("east"));
    startAngle = 90;
  }

  public void setLoc(GridLoc loc) {
    super.setLoc(loc);
    x1 = 0.5;
    y1 = 0.5;
  }

  public String toString() {
    return "SERail";
  }

  ;
}

