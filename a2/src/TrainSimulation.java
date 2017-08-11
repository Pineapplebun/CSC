import java.awt.*;

/**
 * Class TrainSimulation. Simulate trains running around a track.
 * The class TrainSimulation contains all the methods and instance variables
 * necessary to keep track of and run the train simulation.
 */

public class TrainSimulation extends Frame {

  /**
   * The Tracks on which the Trains run.
   */
  private Track[] tracks = new Track[4];

  /**
   * The Trains running on the Tracks.
   */
  private Train[] trains = new Train[8];

  /**
   * The ThreadGroups; all Trains running on the same Track are in the same ThreadGroup.
   */
  ThreadGroup[] TG = new ThreadGroup[8];

  /**
   * Main method.
   */
  public static void main(String[] args) {

    TrainSimulation T = new TrainSimulation();

    // Track 2.
    T.tracks[0] = new Track(20, 20, true);

    // Add 6 new rails and modify 2 existing rails
    Track Track1 = T.tracks[0];
    Track1.addRail(new StraightRail("EW"), 1, 7);
    Track1.addRail(new StraightRail("EW"), 1, 8);
    Track1.addRail(new StraightRail("EW"), 1, 9);
    Track1.addRail(new CornerRail("SW"), 1, 10);
    Track1.addRail(new CornerRail("NW"), 2, 10);
    Track1.addRail(new StraightRail("EW"), 2, 9);
    Track1.addRail(new StraightRail("EW"), 2, 8);
    Track1.addRail(new CornerRail("SE"), 2, 7);

    Track1.initializeTrack();

    T.tracks[0].resize(1024, 768);
    T.tracks[0].move(0, 0);
    T.tracks[0].setBackground(Color.white);
    T.tracks[0].show();

    T.trains[0] = new Train("Train 0");
    T.trains[0].addToTrain(new Caboose());

    T.trains[1] = new Train("Train 1");
    T.trains[1].addToTrain(new Engine());
    T.trains[1].addToTrain(new Caboose());

    T.trains[0].addToTrack(T.tracks[0], new Direction("west"), new GridLoc(2, 3));
    T.trains[0].setSpeed(620);
    T.trains[1].addToTrack(T.tracks[0], new Direction("west"), new GridLoc(1, 6));
    T.trains[1].setSpeed(350);


  }
}

