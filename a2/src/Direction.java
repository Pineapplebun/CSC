/**
 * A direction of 'north', 'south', 'east' or 'west'.
 */
class Direction {

  /**
   * The string representation of this Direction.
   */
  public String direction;

  /**
   * Creates a Direction object. The system exits immediately if a string that is not "north",
   * "south", "east" or "west" is given.
   * @param s the string representation of this Direction.
   */
  public Direction(String s) {

    if (!s.equals("north") && !s.equals("south") &&
        !s.equals("east") && !s.equals("west")) {
      System.err.println(s + " is an invalid direction.  Must be " +
          "'north', 'south', 'east' or 'west'");
      System.exit(0);
    }

    direction = s;

  }

  /**
   * Returns whether a Direction d is the same as another Direction object.
   * @param d the Direction object to be compared.
   * @return true iff both Direction objects are equal.
   */
  public boolean equals(Direction d) {
    return d.equals(direction);
  }

  /**
   * Returns whether a Direction d is the same as another Direction object.
   * @param s the string representation of a direction.
   * @return true iff the Direction object is the same as s.
   */
  public boolean equals(String s) {
    return s.equals(direction);
  }

  public String toString() {
    return direction;
  }

  /**
   * Return the opposite direction to the current object direction.
   * @return the opposite Direction object.
   */
  public Direction opposite() {
    Direction d = null;
    if (direction.equals("north")) {
      d = new Direction("south");
    } else if (direction.equals("south")) {
      d = new Direction("north");
    } else if (direction.equals("east")) {
      d = new Direction("west");
    } else if (direction.equals("west")) {
      d = new Direction("east");
    }

    return d;
  }

}

