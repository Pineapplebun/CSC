import java.awt.*;

/**
 * A TrackPanel for displaying the graphics and panels.
 * The following items are used for double buffering: backBuffer and backG.
 */
class TrackPanel extends Panel {

  /**
   * The buffer in which to draw the image; used for double buffering.
   */
  private Image backBuffer;

  /**
   * The graphics context to use when double buffering.
   */
  private Graphics backG;

  /**
   * Add a Rail 2D array grid to the panel and graphics.
   *
   * @param r a 2D array of Rail.
   */

  void addToPanel(Rail[][] r) {
    Rail[][] rails;
    rails = r;

    setLayout(new GridLayout(rails.length, rails[0].length, 0, 0));

    for (int row = 0; row < rails.length; row++) {
      for (int col = 0; col < rails[0].length; col++) {
        add("", rails[row][col]);
      }
    }
  }

  /**
   * Paint the display.
   * @param g the graphics.
   */
  public void paint(Graphics g) {
    update(g);
  }

  /**
   * Return the Insets.
   * @return the Insets.
   */
  public Insets insets() {
    return new Insets(10, 10, 10, 10);
  }

  /**
   * Update the display; tell all my Tracks to update themselves.
   */
  public void update(Graphics g) {

    // Get my width and height.
    int w = bounds().width;
    int h = bounds().height;

    // If we don't yet have an Image, create one.
    if (backBuffer == null
        || backBuffer.getWidth(null) != w
        || backBuffer.getHeight(null) != h) {
      backBuffer = createImage(w, h);
      if (backBuffer != null) {

        // If we have a backG, it belonged to an old Image.
        // Get rid of it.
        if (backG != null) {
          backG.dispose();
        }
        backG = backBuffer.getGraphics();
      }
    }

    if (backBuffer != null) {

      // Fill in the Graphics context backG.
      g.setColor(Color.white);
      g.fillRect(0, 0, w, h);
    }

  }
}

