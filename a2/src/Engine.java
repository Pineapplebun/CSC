import java.awt.*;

/**
 * The Engine class.  An Engine object is a Car that has an engine,
 * which varies in power depending on weight and power.
 */
class Engine extends Car {

  /**
   * The amount of power in this Engine.
   */
  private int power;

  /**
   * Create an Engine Car. Starts off with 200 weight, 9000 power and green color.
   */
  public Engine() {
    color = Color.green;
    weight = 200;
    power = 9000;
  }

  /**
   * Returns the amount of power for this Engine.
   * @return the amount of power.
   */
  int getPower() {
    return power;
  }

  public String toString() {
    return "Engine";
  }

  ;

}

