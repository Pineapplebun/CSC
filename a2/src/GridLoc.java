/**
 * The (x,y) location on the Track.
 */
public class GridLoc {
  int row;
  int col;

  /**
   * Creates a GridLoc class at row number r and column number c.
   *
   * @param r the row number
   * @param c the column number
   */
  public GridLoc(int r, int c) {
    row = r;
    col = c;
  }

  public String toString() {
    return (row + " " + col);
  }

  ;
}

