/**
 * The EmptyRail class.  This is a place on the Track which does not have an actual piece of track.
*/
class EmptyRail extends Rail {

  /**
   * Return true if d is a valid direction for me.
   */
  public boolean exitOK(Direction d) {
    return false;
  }

  /** Register that Rail r is in Direction d.
   * @param r the Rail to be registered.
   * @param d the direction towards Rail r.
   */
  public void register(Rail r, Direction d) {
  }

  /** Register that there is no Rail in Direction d. */
  public void unRegister(Direction d) {
  }

  /**
   * Given that d is the Direction from which a Car entered, report where the Car will exit.
   * @param d the Rail in Direction d
   * @return d the same Direction d
   */
  public Direction exit(Direction d) {
    return d;
  }

  /**
   * Given that d is the Direction from which a Car entered, report which Rail is next.
   * @param d the direction from which a Car entered.
   * @return an EmptyRail.
   */
  public Rail nextRail(Direction d) {
    return this;
  }

  protected String direction() {
    return "";
  }
}

