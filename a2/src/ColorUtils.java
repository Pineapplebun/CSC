import java.awt.Color;

/**
 * Color utilities that manage color.
 */
public class ColorUtils {

  /**
   * Return a color similar to c, but brighter by amount f.  (The
   * closer f is to 0, the larger the difference.)
   */
  static Color brighter(Color c, double f) {
    return new Color(Math.min((int) (c.getRed() * (1 / f)), 255),
        Math.min((int) (c.getGreen() * (1 / f)), 255),
        Math.min((int) (c.getBlue() * (1 / f)), 255));
  }

  /**
   * Returns a color similar to c, but darker by amount f.  (The
   * closer f is to 0, the larger the difference.)
  */
  static Color darker(Color c, double f) {
    return new Color(Math.max((int) (c.getRed() * f), 0),
        Math.max((int) (c.getGreen() * f), 0),
        Math.max((int) (c.getBlue() * f), 0));
  }
}

