import java.awt.*;

/**
 * The CrossRail class.  A CrossRail object has four ends whose
 * (end1,end2) and (end3,end4) are the two pairs of opposite Directions.
 */
class CrossRail extends Rail {

  /**
   * The ends of the CrossRail which are in order, always 'north', 'south', 'east', and 'west'.
   */
  private Direction end1, end2, end3, end4;

  /**
   * The corresponding Rails that are adjacent to this Rail in the direction of north, east, south
   * or west. The numbering corresponds to each end.
   */
  private Rail neighbour1;  // The Rail in the direction end1.
  private Rail neighbour2;  // The Rail in the direction end2.
  private Rail neighbour3;  // The Rail in the direction end3.
  private Rail neighbour4;  // The Rail in the direction end4.

  /**
   * Returns whether or not a Rail has can exit in a certain direction from the current Rail.
   *
   * @param d the direction to exit the current Rail from.
   * @return whether the exit exists.
   */
  public boolean exitOK(Direction d) {
    return d.equals(end1) || d.equals(end2) || d.equals(end3) || d.equals(end4);
  }

  /**
   * Create a CrossRail without a location."
   */
  public CrossRail() {
    color = Color.darkGray;
    end1 = new Direction("north");
    end2 = new Direction("south");
    end3 = new Direction("east");
    end4 = new Direction("west");
    direction = "nesw";
  }

  /**
   * Create a CrossRail with a location."
   * @param loc the location of the CrossRail.
   */
  public CrossRail(GridLoc loc) {
    super(loc);
    color = Color.darkGray;
    end1 = new Direction("north");
    end2 = new Direction("south");
    end3 = new Direction("east");
    end4 = new Direction("west");
    direction = "nesw";
  }

  /**
   * Registers a Direction d to one end of the Rail.
   * @param r a Rail.
   * @param d a Direction.
   */
  public void register(Rail r, Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = r;
      } else if (d.equals(end2)) {
        neighbour2 = r;
      } else if (d.equals(end3)) {
        neighbour3 = r;
      } else if (d.equals(end4)) {
        neighbour4 = r;
      }
    }
  }

  /**
   * Unregisters a Direction from an end of a CrossRail.
   * @param d a Direction.
   */
  public void unRegister(Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = null;
      } else if (d.equals(end2)) {
        neighbour2 = null;
      } else if (d.equals(end3)) {
        neighbour3 = null;
      } else if (d.equals(end4)) {
        neighbour4 = null;
      }
    }
  }

  /** Given that d is the Direction from which a Car entered,
   * report where the Car will exit.
   * @param d the direction the car entered
   * @return the exit Direction
   */

  public Direction exit(Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        return end2;
      } else if (d.equals(end2)) {
        return end1;
      } else if (d.equals(end3)) {
        return end4;
      } else if (d.equals(end4)) {
        return end3;
      }
    }

    return d;
  }

  /** Return the Rail at the other end. If d is the direction that I entered from,
   * it must be one of end1 and end2 or one of end3 and end4.
   * @param d the direction the Car entered into currentRail
   * @return the next Rail in the exit direction
   */
  public Rail nextRail(Direction d) {
    if (validDir(d)) {
      Direction nD = this.exit(d).opposite();
      if (d.equals(end1)) {
        if ((neighbour2.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour2;
        }
      } else if (d.equals(end2)) {
        if ((neighbour1.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour1;
        }
      } else if (d.equals(end3)) {
        if ((neighbour4.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour4;
        }
      } else if (d.equals(end4)) {
        if ((neighbour3.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour3;
        }
      }
    }
    return this;
  }

  /**
   * Returns the direction type of CrossRail.
   * @return the direction type of CrossRail.
   */
  protected String direction() {
    return direction;
  }

  /**
   * Draws the CrossRail based on the color, x,y, coordinates, width, height.
   * @param g the graphics
   */
  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(color);
    Rectangle b = bounds();
    double x1 = 0.0, y1 = 0.5, x2 = 1.0, y2 = 0.5, x3 = 0.5, y3 = 0.0, x4 = 0.5, y4 = 1.0;
    g.drawLine((int) (x1 * b.width), (int) (y1 * b.height),
        (int) (x2 * b.width), (int) (y2 * b.height));
    g.drawLine((int) (x3 * b.width), (int) (y3 * b.height),
        (int) (x4 * b.width), (int) (y4 * b.height));
  }

  public String toString() {
    return "CrossRail";
  }

  ;
}

