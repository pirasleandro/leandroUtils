package com.github.pirasleandro.containers;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Enumeration used to determine the slot of a triangular coordinate.
 */
public enum Slot {
  A,
  B;

  /**
   * A method used to get the vertical direction of the given slot.
   * Slot A in a cell has only a lower wall, Slot B has only an upper wall.
   * 
   * @param slot  slot which the direction is asked of
   * @return      vertical direction of the given slot
   */
  public static Direction vertDirOf(Slot slot) {
    return (slot == Slot.A ? Direction.DOWN : Direction.UP);
  }

  /**
   * A method used to get the vertical direction of the slot.
   * 
   * @return  vertical direction of this
   * 
   * @see com.github.pirasleandro.containers.Slot#vertDirOf(Slot)
   */
  public Direction vertDir() {
    return vertDirOf(this);
  }

  /**
   * A quality of life method used to make it easier to check if the slot equals Slot.A
   * 
   * @return  true if it's A, flase if it's not
   */
  public boolean isA() {
    return (this == Slot.A);
  }

  /**
   * A quality of life method used to make it easier to check if the slot equals Slot.B
   * 
   * @return  true if it's B, false if it's not
   */
  public boolean isB() {
    return !isA();
  }

  /**
   * A method used to get the other slot of this.
   * 
   * @return  other slot of this
   */
  public Slot other() {
    if (this.isA()) {
      return Slot.B;
    } else {
      return Slot.A;
    }
  }
  
  /**
   * A method used to convert the given direction depending on the slot.
   * 
   * @param direction direction to be converted
   * @return          converted direction
   */
  public Direction convertDir(Direction direction) {
    if (direction.isVertical()) {
      return this.vertDir();
    } else {
      return direction;
    }
  }
}