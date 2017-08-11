import java.awt.*;

/**
 * The Rail class.  A Rail object is a piece of track.  It knows
 * whether there is a Train on it or not, and Trains can enter()
 * and leave().  Given an entrance, a Rail can report where the exit()
 * is.
 */

abstract class Rail extends Canvas {

  /**
   * The color of a generic Rail.
   */
  protected Color color;

  /**
   * Whether or not this Rail is occupied by a Train.
   */
  private boolean haveATrain;

  /**
   * The current Car that is occupying this Rail.
   */
  private Car currentCar;

  /**
   * The current location of the Rail.
   */
  private GridLoc location;

  /**
   * The direction type of the Rail.
   */
  String direction;

  /**
   * Create a Rail using a GridLoc location. It will be initialized to have no Train at first.
   * @param loc the location of the Rail.
   */
  public Rail(GridLoc loc) {
    location = loc;
    haveATrain = false;
    Rectangle b = bounds();
    int size = 20;
    resize(size, size);
  }

  /**
   * Create a Rail without a location.
   */
  public Rail() {
    super();
    haveATrain = false;
    Rectangle b = bounds();
    int size = 20;
    resize(size, size);
  }

  /**
   * Return true iff I have a Car.
   * @return true if the rail is occupied
   */
  boolean occupied() {
    return haveATrain;
  }

  /**
   * Set the location of this Rail.
   * @param loc the location of this Rail.
   */
  public void setLoc(GridLoc loc) {
    location = loc;
  }

  /**
   * Retrieve the location of this Rail.
   * @return the location of this Rail
   */
  public GridLoc getLoc() {
    return location;
  }

  /**
   * Draws the generic Rail.
   * @param g the graphics.
   */
  void draw(Graphics g) {

    Rectangle b = bounds();
    g.setColor(Color.white);
    g.fillRect(0, 0, b.width - 1, b.height - 1);
    g.setColor(Color.lightGray);
    g.drawRect(0, 0, b.width - 1, b.height - 1);

    if (haveATrain) {
      currentCar.draw(g);
    }
  }

  /**
   * Register that a Train is on me.  Return true if successful, false otherwise.
   * Set haveATrain true and the currentCar to be the new Car if the Car enters.
   * @param newCar the Car that attempts to enter this Rail.
   */
  void enter(Car newCar) {
    haveATrain = true;
    currentCar = newCar;
  }

  /** Register that a Train is no longer on me.
   * Set haveATrain false.
   */
  void leave() {
    haveATrain = false;
    repaint();
  }

  /**
   * Update my display.
   * @param g the graphics.
   */
  public void paint(Graphics g) {
    draw(g);
  }

  /**
   * Returns whether the exit exists for a Direction d of this Rail.
   * @param d a Direction.
   * @return if the exit exists at a particular direction.
   */
  boolean validDir(Direction d) {
    return exitOK(d);
  }

  /**
   * Return true if d is a valid direction for me.
   * @param d a Direction.
   * @return if the exit exists at Direction d.
   */
  abstract public boolean exitOK(Direction d);

  /**
   * Register that Rail r is in Direction d.
   *
   * @param r the Rail that is to register a Direction d.
   * @param d the Direction to be registered.
   */
  abstract public void register(Rail r, Direction d);

  /**
   * Register that there is no Rail in Direction d.
   * @param d the Direction to unregister from.
   */
  abstract public void unRegister(Direction d);

  /**
   * Given that d is the Direction from which a Car entered, report where the Car will exit.
   *
   * @param d the Direction the Car entered the currentRail.
   * @return the exit direction.
   */
  abstract protected Direction exit(Direction d);

  /**
   * Given that d is the Direction from which a Car entered, report which Rail is next.
   * @param d the Direction of interest.
   * @return the nextRail at Direction d.
   */
  abstract protected Rail nextRail(Direction d);

  /**
   * Return the direction type of this Rail.
   * @return the direction type.
   */
  abstract protected String direction();

  public String toString() {
    return "Rail";
  }
}

