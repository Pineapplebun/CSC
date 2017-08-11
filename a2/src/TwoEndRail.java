import java.awt.*;

/**
 * The TwoEndRail class.  A TwoEndRail object has two ends,
 * which may or may be not be opposite each other. A TwoEndRail is black.
 */
abstract class TwoEndRail extends Rail {

  /**
   * The two Directions in a TwoEndRail class.
   */
  private Direction end1, end2;

  /**
   * The Rail in the direction end1.
   */
  private Rail neighbour1;

  /**
   * The Rail in the direction end2.
   */
  private Rail neighbour2;

  /**
   * Return true if Direction d is one of the two exists for this Rail.
   *
   * @param d a Direction.
   * @return tru iff d is a valid exit.
   */
  public boolean exitOK(Direction d) {
    return d.equals(end1) || d.equals(end2);
  }

  /**
   * Create a TwoEndRail with a location.
   * @param loc the location of the TwoEndRail.
   */
  public TwoEndRail(GridLoc loc) {
    super(loc);
    color = Color.black;
  }

  /**
   * Create a TwoEndRail without a location.
   */
  public TwoEndRail() {
    super();
    color = Color.black;
  }

  /**
   * Sets the direction of each end from the current Rail.
   * @param dir1 the direction towards end1
   * @param dir2 the direction towards end2
   */
  void setDirection(Direction dir1, Direction dir2) {
    end1 = dir1;
    end2 = dir2;
  }

  /**
   * Returns the direction type of this Rail.
   * @return the direction type.
   */
  protected String direction() {
    return direction;
  }

  /**
   * Register that r is adjacent to me from direction d.
   * @param r the Rail that is to register a Direction d.
   * @param d the Direction to be registered.
   */
  public void register(Rail r, Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = r;
      } else {
        neighbour2 = r;
      }
    }
  }

  /**
   * Unregister that r is adjacent to me from direction d.
   * @param d the Direction to unregister from.
   */
  public void unRegister(Direction d) {
    if (validDir(d)) {
      if (d.equals(end1)) {
        neighbour1 = null;
      } else if (d.equals(end2)) {
        neighbour2 = null;
      }
    }
  }

  /**
   * Given that d is the Direction from which a Car entered, report where the Car will exit.
   * @param d the Direction the Car entered the currentRail.
   * @return the Direction to exit the currentRail.
   */
  public Direction exit(Direction d) {
    if (validDir(d)) {
      return d.equals(end1) ? end2 : end1;
    }
    return d;
  }

  /**
   * Return the Rail at the other end. If d is the direction that the Car entered from,
   * then it must be one of end1 and end2.
   *
   * @param d the direction that the Car entered from.
   * @return the next Rail.
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
      }
      if (d.equals(end2)) {
        if ((neighbour1.exit(nD)).equals(nD)) {
          return this;
        } else {
          return neighbour1;
        }
      } else {
        return this;
      }
    }
    return this;
  }

  public String toString() {
    return "TwoEndRail";
  }
}

