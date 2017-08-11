import java.awt.*;

/**
 * The Track class.  A Track object is made up of Rails, and
 * has zero or more trains in it.
 */
class Track extends Frame {

  /**
   *  The Panel on which the Track appears.
   */
  private TrackPanel trackPanel;

  /**
   * The max number of Trains.
   */
  private int MAX_TRAINS = 10;

  /**
   * The trainList.
   */
  private Train[] trainList = new Train[MAX_TRAINS];    // The Trains running on me.

  /**
   * The number of Trains.
   */
  private int numTrains = 0;

  /**
   * A grid of rails.
   */
  private Rail[][] rails;

  /**
   * Builds a Track and Buttons for the GUI.
   */
  private void buildTrack() {
    Button runStopButton, quitButton;
    Button accelButton, decelButton, accelLotsButton, decelLotsButton;
    trackPanel = new TrackPanel();
    add("Center", trackPanel);

    runStopButton = new Button("Run");
    quitButton = new Button("Quit");
    accelButton = new Button("Accelerate");
    decelButton = new Button("Decelerate");
    accelLotsButton = new Button("Accelerate A Lot");
    decelLotsButton = new Button("Decelerate A Lot");

    Panel p2 = new Panel();
    p2.setLayout(new GridLayout(0, 1));
    p2.add(runStopButton);
    p2.add(accelLotsButton);
    p2.add(decelLotsButton);
    p2.add(accelButton);
    p2.add(decelButton);
    p2.add(quitButton);
    add("East", p2);

    pack();
  }

  /**
   * Read Rail-placing commands from the user.
   *
   * @param evt
   * @return boolean
   */
  public boolean handleEvent(Event evt) {
    Object target = evt.target;

    if (evt.id == Event.ACTION_EVENT) {
      if (target instanceof Button) {
        if ("Run".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].start();
          }
          ((Button) target).setLabel("Suspend");
        } else if ("Suspend".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].suspend();
          }
          ((Button) target).setLabel("Resume");
        } else if ("Resume".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].resume();
          }
          ((Button) target).setLabel("Suspend");
        } else if ("Accelerate".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].accelerate();
          }
        } else if ("Decelerate".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].decelerate();
          }
        } else if ("Accelerate A Lot".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].accelerateALot();
          }
        } else if ("Decelerate A Lot".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].decelerateALot();
          }
        } else if ("Quit".equals(evt.arg)) {
          for (int i = 0; i < numTrains; i++) {
            trainList[i].stop();
          }
          System.exit(0);
        }
        return true;
      }
    }

    // If we get this far, I couldn't handle the event
    return false;
  }


  /**
   * If test and r1 != null and r1.exitOK(r1), connect or unregister
   * r1 and r2 depending on whether r2's exits match r1's.
   */
  private void registerOrUnRegister(boolean test, Rail r1, Rail r2, Direction d) {
    if (test && r1 != null && r1.exitOK(d)) {
      if (r2.exitOK(d.opposite())) {
        connectRails(r1, r2, d);
      } else {
        r1.unRegister(d);
      }
    }
  }

  /**
   * Connect the Rail at (row,col) to its neighbours.
   * @param row the row number
   * @param col the column number
   */
  private void connectRail(int row, int col) {
    Rail r = rails[row][col];

    Direction north = new Direction("north");
    Direction south = new Direction("south");
    Direction east = new Direction("east");
    Direction west = new Direction("west");

    if (r != null) {
      Rail rN = row > 0 ? rails[row - 1][col] : null;
      Rail rS = row < rails.length - 1 ? rails[row + 1][col] : null;
      Rail rE = col < rails[0].length - 1 ? rails[row][col + 1] : null;
      Rail rW = col > 0 ? rails[row][col - 1] : null;

      registerOrUnRegister(row > 0, rN, r, south);
      registerOrUnRegister(row < rails.length - 1, rS, r, north);
      registerOrUnRegister(col > 0, rW, r, east);
      registerOrUnRegister(col < rails[0].length - 1, rE, r, west);
    }
  }


  /** Connect rails r1 and r2; r2 is in direction d from r1.
   *
   * @param r1 the Rail that is in the direction r2 from r1
   * @param r2 the Rail that is in the direction r2 from r1
   * @param d the direction of r2 from r1
   */
  private void connectRails(Rail r1, Rail r2, Direction d) {
    r1.register(r2, d);
    r2.register(r1, d.opposite());
  }

  /**
   * Add e to the rail at location loc.
   * @param loc
   * @param e
   */
  void addCar(GridLoc loc, Car e) {
    rails[loc.row][loc.col].enter(e);
    e.setRail(rails[loc.row][loc.col]);
  }

  /**
   * Paint the system.
   * @param g the graphics.
   */
  public void paint(Graphics g) {
    update(g);
  }

  /**
   * Update the graphics.
   * @param g the graphics.
   */
  public void update(Graphics g) {
    trackPanel.repaint();
  }

  /**
   * Add a Train T to myself.
   * @param T the Train.
   */
  void addTrain(Train T) {
    trainList[numTrains] = T;
    numTrains++;
  }

  /**
   * Set up a new, simple Track.
   * @param x the location x
   * @param y the location y
   * @param defaultTrack whether to use the default track or not.
   */
  public Track(int x, int y, boolean defaultTrack) {
    rails = new Rail[x][y];

    buildTrack();

    for (int row = 0; row < rails.length; row++) {
      for (int col = 0; col < rails[0].length; col++) {
        rails[row][col] = new EmptyRail();
      }
    }

    if (defaultTrack) {
      defaultTrack(rails);
    }

    for (int row = 0; row < rails.length; row++) {
      for (int col = 0; col < rails[0].length; col++) {
        connectRail(row, col);
      }
    }
  }

  /**
   * Modifies the track to be the default track mentions.
   * @param track
   */
  private void defaultTrack(Rail[][] track) {

    track[0][0] = new CornerRail("SE", new GridLoc(0, 0));
    track[0][1] = new StraightRail("EW", new GridLoc(0, 1));
    track[0][2] = new CornerRail("SW", new GridLoc(0, 2));
    track[1][2] = new StraightRail("NS", new GridLoc(1, 2));
    track[2][2] = new CrossRail(new GridLoc(2, 2));
    track[2][3] = new StraightRail("EW", new GridLoc(2, 3));
    track[2][4] = new StraightRail("EW", new GridLoc(2, 4));
    track[2][5] = new CrossRail(new GridLoc(2, 5));
    track[2][6] = new CornerRail("SW", new GridLoc(2, 6));
    track[3][6] = new CornerRail("NW", new GridLoc(3, 6));
    track[3][5] = new CornerRail("NE", new GridLoc(3, 5));
    track[1][5] = new CornerRail("SE", new GridLoc(1, 5));
    track[1][6] = new StraightRail("EW", new GridLoc(1, 6));
    track[1][7] = new CornerRail("SW", new GridLoc(1, 7));
    track[2][7] = new StraightRail("NS", new GridLoc(2, 7));
    track[3][7] = new StraightRail("NS", new GridLoc(3, 7));
    track[4][7] = new CornerRail("NW", new GridLoc(4, 7));
    track[4][6] = new StraightRail("EW", new GridLoc(4, 6));
    track[4][5] = new SwitchRail("WES", new GridLoc(4, 5));
    track[4][4] = new CornerRail("NE", new GridLoc(4, 4));
    track[3][4] = new CornerRail("SW", new GridLoc(3, 4));
    track[3][3] = new SwitchRail("EWS", new GridLoc(3, 3));
    track[4][3] = new SwitchRail("SNW", new GridLoc(4, 3));
    track[5][3] = new CornerRail("NE", new GridLoc(5, 3));
    track[5][4] = new StraightRail("EW", new GridLoc(5, 4));
    track[5][5] = new CornerRail("NW", new GridLoc(5, 5));
    track[6][0] = new SwitchRail("EWN", new GridLoc(6, 0));
    track[6][1] = new SwitchRail("EWS", new GridLoc(6, 1));
    track[6][2] = new SwitchRail("WEN", new GridLoc(6, 2));
    track[6][3] = new SwitchRail("WES", new GridLoc(6, 3));
    track[6][4] = new SwitchRail("NSE", new GridLoc(6, 4));
    track[6][5] = new SwitchRail("NSW", new GridLoc(6, 5));
    track[6][6] = new SwitchRail("SNE", new GridLoc(6, 6));
    track[6][7] = new SwitchRail("SNW", new GridLoc(6, 7));
    track[3][2] = new CrossRail(new GridLoc(3, 2));
    track[4][2] = new SwitchRail("WEN", new GridLoc(4, 2));
    track[4][1] = new CornerRail("NE", new GridLoc(4, 1));
    track[3][1] = new CornerRail("SE", new GridLoc(3, 1));
    track[2][1] = new StraightRail("EW", new GridLoc(2, 1));
    track[2][0] = new CornerRail("NE", new GridLoc(2, 0));
    track[1][0] = new StraightRail("NS", new GridLoc(1, 0));
  }

  /**
   * Adds a Rail to the current Track.
   * @param newRail the Rail to add.
   * @param x the x coordinate.
   * @param y the y coordinate.
   */
  void addRail(Rail newRail, int x, int y) {
    if (x < rails.length && y < rails.length) {
      newRail.setLoc(new GridLoc(x, y));
      rails[x][y] = newRail;
      connectRail(x, y);
    }
  }

  /**
   * Initializes the table and the rails.
   */
  void initializeTrack() {
    trackPanel.addToPanel(rails);
  }
}
