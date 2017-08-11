import java.awt.*;

/**
 * A Caboose is a Car that can follow the rest
 * of the train.  It is red.
 */
class Caboose extends Car {

  /**
   * Creates a new Caboose object that is red and has weight 50.
   */

  public Caboose() {
    color = Color.red;
    weight = 50;
  }

  public String toString() {
    return "Caboose";
  }
}

