package containers;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Enumeration used for the basic directions UP, DOWN, LEFT, RIGHT
 * It is advised to always use the directions in the previous mentioned order to avoid disarray.
 */
public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  /**
   * A method used to get the opposite direction of the given direction.
   * 
   * @param direction direction that the opposite is asked of
   * @return          opposite direction of the given direction
   */
  public static Direction oppositeOf(Direction direction) {
    switch (direction) {
      case UP: return DOWN;
      case DOWN: return UP;
      case LEFT: return RIGHT;
      case RIGHT: return LEFT;
      default: throw new EnumConstantNotPresentException(Direction.class, direction.toString());
    }
  }

  /**
   * A method used to get the opposite direction of itself
   * 
   * @return  opposite direction of itself
   */
  public Direction opposite() {
    return oppositeOf(this);
  }

  /**
   * A method used to get the direction clockwise of the given direction.
   * 
   * @param direction the direction the clockwise direction is asked of
   * @return          the clockwise direction of the given direction
   */
  public static Direction clockwiseOf(Direction direction) {
    switch (direction) {
      case UP: return RIGHT;
      case DOWN: return LEFT;
      case LEFT: return UP;
      case RIGHT: return DOWN;
      default: throw new EnumConstantNotPresentException(Direction.class, direction.toString());
    }
  }

  /**
   * A method to get the direction counter clockwise of the given direction.
   * 
   * @param direction the direction the counter clockwise direction is asked of
   * @return          the counter clockwise direction of the given direction
   */
  public static Direction counterClockwiseOf(Direction direction) {
    return oppositeOf(clockwiseOf(direction));
  }

  /**
   * A method to get the direction clockwise of this.
   * 
   * @return  the direction clockwise of itself
   * 
   * @see containers.Direction#clockwiseOf(Direction)
   */
  public Direction turnClockwise() {
    return clockwiseOf(this);
  }

  /**
   * A method to get the direction counter clockwise of this.
   * 
   * @return  the direction counter clockwise of itself
   * 
   * @see containers.Direction#counterClockwiseOf(Direction)
   */
  public Direction turnCounterClockwise() {
    return counterClockwiseOf(this);
  }

  /**
   * A method used to determine if the two given directions are alligned.
   * Directions count as alligned if they are both vertical or both horizontal.
   * 
   * @param direction1  first direction
   * @param direction2  second direction
   * @return            true if they are alligned, false if they are not
   */
  public static boolean areAligned(Direction direction1, Direction direction2) {
    if (direction1 == direction2 || direction1 == direction1.opposite()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method used to determine if this is aligned with the given direction.
   * 
   * @param direction2  direction that will be tested
   * @return            true if they are aligned, false if they are not
   */
  public boolean alignedWith(Direction direction2) {
    return areAligned(this, direction2);
  }

  /**
   * A method to determine if the direction is vertical.
   * 
   * @return  true if it's vertical, false if it's not
   */
  public boolean isVertical() {
    switch (this) {
      case UP:
      case DOWN: return true;
      case LEFT:
      case RIGHT: return false;
      default: throw new EnumConstantNotPresentException(Direction.class, this.toString());
    }
  }

  /**
   * A method to determine if the direction is horizontal.
   * 
   * @return  true if it's horizontal, false if it's not
   */
  public boolean isHorizontal() {
    switch (this) {
      case UP:
      case DOWN: return false;
      case LEFT:
      case RIGHT: return true;
      default: throw new EnumConstantNotPresentException(Direction.class, this.toString());
    }
  }
}