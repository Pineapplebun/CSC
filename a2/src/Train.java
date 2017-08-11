/**
The Train class.  Trains have an Engine, followed by one or more Cars,
followed by a Caboose.  There is no limit to the length of a Train.  The
train has a speed, which is related to the size of the engine, the weight
of the whole train, and the amount of power flowing through the tracks.

A train has a delay, which is directly related to the speed -- the faster
the train is moving, the shorter the delay.  Each turn, a Train will move
one track piece in the current direction.

*/
class Train extends Thread {

  /**
   * The sum of the weights of my cars.
   */
  private int totalWeight;

  /**
   * // The amount of time between each of my turns.
   */
  private int delay;

  /**
   * The Car pulling the train.
   */
  private Engine engine;

  /**
   * The Car at the end of the train.
   */
  private Car caboose;

  /**
   * Create a Train. This Train already has 2 Cars, 1 Engine and 1 Caboose.
   *
   * @param threadName the name of the thread.
   */
  public Train(String threadName) {
    super(threadName);
    engine = new Engine();
    totalWeight += engine.weight;
    caboose = engine;
  }

  /**
   * Add Car T to the end of me.
   * @param T the Car to be added to the Train.
   */
  void addToTrain(Car T) {
    caboose.nextCar = T;
    caboose = T;
    totalWeight += T.weight;
  }

  /**
   * Set my delay between moves to d.
   * @param d the delay in speed.
   */
  void setSpeed(int d) {
    delay = d;
  }

  /**
   * Add me to Track T at location loc moving in direction dir.
   * @param T the Track where the Train is added.
   * @param dir the Direction the Train starts at the Track.
   * @param loc the location of the Train.
   */
  void addToTrack(Track T, Direction dir, GridLoc loc) {
    Track theTrack;
    theTrack = T;
    theTrack.addTrain(this);

    Car currCar = engine;
    while (currCar != null) {
      currCar.setDirection(dir);
      theTrack.addCar(loc, currCar);

      // Now figure out the dir for the next Car,
      // and the next loc.

      if (dir.equals("north")) {
        loc.row--;
      } else if (dir.equals("south")) {
        loc.row++;
      } else if (dir.equals("east")) {
        loc.col++;
      } else if (dir.equals("west")) {
        loc.col--;
      }

      Direction nD = currCar.currentRail.exit(dir);
      Rail nextRail = currCar.currentRail.nextRail(nD);

      // Now I know the Rail on which the next currCar will
      // be.  Find out how it got on to it.
      dir = nextRail.exit(dir.opposite());

      currCar = currCar.nextCar;
    }
  }

  /**
   * Decrease my delay sharply based on totalWeight and engine power.
   */
  void accelerateALot() {
    delay /= engine.getPower() / totalWeight;
  }

  /**
   * Increase my delay sharply based on totalWeight and engine power.
   */
  void decelerateALot() {
    delay *= engine.getPower() / totalWeight;
  }

  /**
   * Decrease my delay based on totalWeight and engine power.
   */
  void accelerate() {
    delay -= engine.getPower() / totalWeight;
  }

  /**
   * Increase my delay based on totalWeight and engine power.
   */
  void decelerate() {
    delay += engine.getPower() / totalWeight;
  }

  /**
   * Move the Train based on the delay.
   */
  public void run() {
    while (true) {
      engine.move();
      // Sleep for 1 second.
      try {
        sleep(delay);
      } catch (InterruptedException e) {
      }
    }
  }

}

