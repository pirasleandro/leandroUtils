package com.github.pirasleandro.containers;

import com.github.pirasleandro.characters.Ansi;
import com.github.pirasleandro.characters.Box;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Storage class used to store normal and wall values in a triangular grid-like structure.
 * @see com.github.pirasleandro.containers.Grid
 * 
 * @param T type of normal storage values
 * @param W type of wall values
 */
public class TriGrid<T,W> {
  private final int size;
  private Grid<T,W> grid;
  
  /**
   * Empty constructor to avoid empty TriGrid Objects
   */
  @SuppressWarnings("unused")
  private TriGrid() {
    this.size = 0;
  }

  /**
   * A basic constructor
   * 
   * @param size  size of the TriGrid
   */
  public TriGrid(int size) {
    this.size = size;
    grid = new Grid<T,W>((size*2)-1, size);
  }

  /**
   * A constructor that additionally sets all values to the given default values
   * 
   * @param size        size of the TriGrid
   * @param defaultCell value all cells will be set to
   * @param defaultWall value all walls will be set to
   * 
   * @see com.github.pirasleandro.containers.TriGrid#TriGrid(int)
   * @see com.github.pirasleandro.containers.TriGrid#fill(Object, Object)
   */
  public TriGrid(int size, T defaultCell, W defaultWall) {
    this(size);
    fill(defaultCell, defaultWall);
  }

  /**
   * A method used to set the value of the given slot in the cell at the given coordinates.
   * 
   * @param x     x coordinate of the cell
   * @param y     y coordinate of the cell
   * @param slot  slot of the cell
   * @param value value the slot will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   */
  public void set(int x, int y, Slot slot, T value) {
    validateCords(x, y, slot);
    grid.set(convertX(x, slot), y, value);
  }

  /**
   * A method used to set the value of the slot at the given TriPoint.
   * 
   * @param triPoint  TriPoint of the slot
   * @param value     value the slot will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   */
  public void set(TriPoint triPoint, T value) {
    set(triPoint.x, triPoint.y, triPoint.slot, value);
  }

  /**
   * A method used to set the value of the wall in the given direction relative to the given slot
   * of the cell at the given coordinates.
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param slot      slot of the cell
   * @param direction direction from slot to wall
   * @param value     value the wall will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   */
  public void setWall(int x, int y, Slot slot, Direction direction, W value) {
    validateCords(x, y, slot);
    validateDirection(direction, slot);
    if (direction.isVertical()) {
      grid.setWall(convertX(x, Slot.A), y, slot.vertDir(), value);
      if (convertX(x, Slot.B) < (size*2)-1) {
        grid.setWall(convertX(x, Slot.B), y, slot.vertDir(), value);
      }
    } else {
      grid.setWall(convertX(x, slot), y, direction, value);
    }
  }

  /**
   * A method used to set the value of the wall in the given direction relative to the given slot
   * of the cell at the given coordinates.
   * 
   * @param triPoint  TriPoint of the slot
   * @param direction direction from TriPoint to wall
   * @param value     value the wall will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#setWall(int, int, Slot, Direction, Object)
   */
  public void setWall(TriPoint triPoint, Direction direction, W value) {
    setWall(triPoint.x, triPoint.y, triPoint.slot, direction, value);
  }

  /**
   * A method used to set the values of the walls of the given slot of the cell at
   * the given coordinates to the given values.
   * 
   * @param x             x coordinate of cell
   * @param y             y coordinate of cell
   * @param slot          slot of cell
   * @param verticalWall  vertical wall value
   * @param leftWall      left wall value
   * @param rightWall     right wall value
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#setWall(TriPoint, Direction, Object)
   */
  public void setWalls(int x, int y, Slot slot, W verticalWall, W leftWall, W rightWall) {
    setWall(x, y, slot, slot.vertDir(), verticalWall);
    setWall(x, y, slot, Direction.LEFT, leftWall);
    setWall(x, y, slot, Direction.RIGHT, rightWall);
  }

  /**
   * A method used to set the values of the walls of the slot at the given TriPoint to the given value.
   * 
   * @param triPoint      TriPoint of the slot
   * @param verticalWall  vertical wall value
   * @param leftWall      left wall value
   * @param rightWall     right wall value
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#setWalls(int, int, Slot, Object, Object, Object)
   */
  public void setWalls(TriPoint triPoint, W verticalWall, W leftWall, W rightWall) {
    setWalls(triPoint.x, triPoint.y, triPoint.slot, verticalWall, leftWall, rightWall);
  }

  /**
   * A method used to set all values of the walls of the given slot of the cell
   * at the given coordinates to the given default value.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param slot        slot of the cell
   * @param defaultWall value all walls will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#setWalls(int, int, Slot, Object, Object, Object)
   */
  public void setWalls(int x, int y, Slot slot, W defaultWall) {
    setWalls(x, y, slot, defaultWall, defaultWall, defaultWall);
  }

  /**
   * A method used to set all values of the walls of the slot at the given TriPoint to the given value.
   * 
   * @param triPoint    TriPoint of the slot
   * @param defaultWall value all walls will be set to
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#setWalls(int, int, Slot, Object)
   */
  public void setWalls(TriPoint triPoint, W defaultWall) {
    setWalls(triPoint.x, triPoint.y, triPoint.slot, defaultWall);
  }

  /**
   * A method used to set all walls on the border to the given default value.
   * Walls count as border if they only have one adjacent cell.
   * 
   * @param defaultWall value all walls will be set to
   */
  public void setBorder(W defaultWall) {
    for (int i = 0; i < size; i++) {
      setWall(i, size-1, Slot.A, Direction.DOWN, defaultWall);
      setWall(0, i, Slot.A, Direction.LEFT, defaultWall);
      setWall(i, i, Slot.A, Direction.RIGHT, defaultWall);
    }
  }

  /**
   * A method used to set all values to the given default values.
   * 
   * @param defaultCell value all cells will be set to
   * @param defaultWall value all walls will be set to
   * 
   * @see com.github.pirasleandro.containers.TriGrid#set(int, int, Slot, Object)
   * @see com.github.pirasleandro.containers.TriGrid#setWalls(int, int, Slot, Object)
   */
  public void fill(T defaultCell, W defaultWall) {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < y+1; x++) {
        set(x, y, Slot.A, defaultCell);
        setWalls(x, y, Slot.A, defaultWall);
        if (hasTwoSlots(x, y)) {
          set(x, y, Slot.B, defaultCell);
          setWalls(x, y, Slot.B, defaultWall);
        }
      }
    }
  }

  /**
   * A method used to calculate the direction which the second slot is relative to the first slot.
   * 
   * @param x1    x coordinate of the first cell
   * @param y1    y coordinate of the first cell
   * @param slot1 slot of the first cell
   * @param x2    x coordinate of the second cell
   * @param y2    y coordinate of the second cell
   * @param slot2 slot of the second cell
   * @return      Direction the second slot is in relative to the first slot
   * 
   * @throws IllegalArgumentException if the two slots are not neighbours
   */
  public Direction calcDirection(int x1, int y1, Slot slot1, int x2, int y2, Slot slot2) {
    if (x1 != x2 && y1 != y2) {
      throw new IllegalArgumentException("It's impossible for two coordinates who neither share the same x value nor the same y value to be neighbours.");
    } else if (slot1.isA() && y1 > y2) {
      throw new IllegalArgumentException("Illegal direction for slot A.");
    } else if (slot1.isB() && y1 < y2) {
      throw new IllegalArgumentException("Illegal direction for slot B.");
    } else if (slot1 == slot2) {
      throw new IllegalArgumentException("Two coordinates with the same slot can't be neighbours.");
    }
    if (y1 == y2) {
      if (x1 == x2) {
        return (slot1.isA() ? Direction.RIGHT : Direction.LEFT);
      } else {
        return (slot1.isA() ? Direction.LEFT : Direction.RIGHT);
      }
    } else {
      return (slot1.isA() ? Direction.DOWN : Direction.UP);
    }
  }

  /**
   * A method used to calculate the direction which the slot at the second given TriPoint is relative to the slot
   * at the first given TriPoint.
   * 
   * @param triPoint1 TriPoint of the first slot
   * @param triPoint2 TriPoint of the second slot
   * @return          Direction the second slot is in relative to the first slot
   * 
   * @see com.github.pirasleandro.containers.TriGrid#calcDirection(int, int, Slot, int, int, Slot)
   */
  public Direction calcDirection(TriPoint triPoint1, TriPoint triPoint2) {
    return calcDirection(triPoint1.x, triPoint1.y, triPoint1.slot, triPoint2.x, triPoint2.y, triPoint2.slot);
  }

  /**
   * A method to determine if the given coordinates are valid.
   * 
   * @param x     x coordinate
   * @param y     y coordinate
   * @param slot  slot
   * @return      true if the coordinates are valid, false if they're not
   */
  public boolean areCordsValid(int x, int y, Slot slot) {
    if (x > size-1 || x < 0 || y > size-1 || y < 0) {
      return false;
    }
    if (slot.isA()) {
      return (y >= x);
    } else {
      return (y > x || hasTwoSlots(x, y));
    }
  }

  /**
   * A method used to determine if the coordinates of the given TriPoint are valid.
   * 
   * @param point TriPoint whose coordinates are to be checked
   * @return      true if the coordinates are valid, false if they're not
   * 
   * @see com.github.pirasleandro.containers.TriGrid#areCordsValid(int, int, Slot)
   */
  public boolean areCordsValid(TriPoint point) {
    return areCordsValid(point.x, point.y, point.slot);
  }

  /**
   * A private method used to determine if the coordinates are valid.
   * Throws IndexOutOfBoundsException if they're not.
   * 
   * @param x     x coordinate
   * @param y     y coordinate
   * @param slot  slot
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#areCordsValid(int, int, Slot)
   */
  private void validateCords(int x, int y, Slot slot) {
    if (!areCordsValid(x, y, slot)) {
      throw new IndexOutOfBoundsException(
        "Index " + x + "/" + y + "/" + slot.toString() + " out of bounds for size " + size + ".");
    }
  }

  /**
   * A private method used to determine if the given direction is valid for the given slot.
   * Throws IllegalArgumentException if it's not.
   * 
   * @param direction direction to be validated
   * @param slot      slot whos direction is to be validated
   * 
   * @throws IllegalArgumentException if the direction is not valid
   */
  private void validateDirection(Direction direction, Slot slot) {
    if (direction.isVertical() && direction != slot.convertDir(direction)) {
      throw new IllegalArgumentException(
        "Cells in slot " + 
        (slot == Slot.A ? 
          "A doesn't have a wall in the direction UP" : 
          "B doesn't have a wall in the direction DOWN"));
    }
  }
  
  /**
   * A method used to convert the given x coordinate from a user friendly coordinate to a Grid coordinate
   * depending on the given slot.
   * 
   * @param x     x coordinate to be converted
   * @param slot  slot of the coordinate
   * @return      converted x coordinate
   */
  private int convertX(int x, Slot slot) {
    if (slot.isA()) {
      return x*2;
    } else {
      return (x*2)+1;
    }
  }

  /**
   * A method used to convert the given x coordinate from a Grid coordinate to an user friendly coordinate
   * depending on the given slot.
   * 
   * @param x     x coordinate to be converted
   * @param slot  slot of the coordinate
   * @return      converted x coordinate
   */
  public static int convertXBack(int x, Slot slot) {
    if (slot.isA()) {
      return x/2;
    } else {
      return (x-1)/2;
    }
  }

  /**
   * A method used to convert the coordinates of the given TriPoint from user freindly coordinates to Grid coordinates.
   * 
   * @param pt  TriPoint to be converted
   * @return    converted TriPoint
   * 
   * @see com.github.pirasleandro.containers.Grid#convert(java.awt.Point)
   */
  public static TriPoint convert(TriPoint pt) {
    return (new TriPoint(Grid.convert(pt), pt.slot));
  }

  /**
   * A method used to determine if call at the given coordinate has two slots.
   * 
   * @param x x coordinate of the cell
   * @param y y coordinate of the cell
   * @return  true if the cell has two slots, false if it has not
   */
  public static boolean hasTwoSlots(int x, int y) {
    return (x != y);
  }

  /**
   * A method used to get an Object with the value of the given slot of the cell at the given coordinates.
   * 
   * @param x     x coordinate of the cell
   * @param y     y coordinate of the cell
   * @param slot  slot of the cell
   * @return      Object with the value of the cell
   * 
   * @throws IndexOutOfBoundsException if the coordinates are not valid
   */
  public Object get(int x, int y, Slot slot) {
    validateCords(x, y, slot);
    return grid.get(convertX(x, slot), y);
  }

  /**
   * A method used to get an Object with the value of the slot at the given TriPoint.
   * 
   * @param point TriPoint of the slot
   * @return      Object with the value of the cell
   * 
   * @throws IndexOutOfBoundsException if the coordinates of the TriPoint are not valid
   * 
   * @see com.github.pirasleandro.containers.TriGrid#get(int, int, Slot)
   */
  public Object get(TriPoint point) {
    return get(point.x, point.y, point.slot);
  }

  /**
   * A method used to get the wall in the given direction relative to the cell at the given coordinates
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param direction direction from slot to wall
   * @return          value of the wall
   * 
   * @throws EnumConstantNotPresentException if the direction is not valid
   */
  public Object getWall(int x, int y, Direction direction) {
    switch (direction) {
      case UP: 
      case LEFT: return getWall(x, y, Slot.B, direction);
      case DOWN: 
      case RIGHT: return getWall(x, y, Slot.A, direction); 
      default: throw new EnumConstantNotPresentException(Direction.class, direction.toString());
    }
  }

  /**
   * A method used to get the wall in the given direction relative to the given slot of
   * the cell at the given coordinates
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param slot      slot of the cell
   * @param direction direction from slot to wall
   * @return          value of the wall
   * 
   * @throws IndexOutOfBoundsException if the coordinates of the TriPoint are not valid
   */
  public Object getWall(int x, int y, Slot slot, Direction direction) {
    validateCords(x, y, slot);
    validateDirection(direction, slot);
    return grid.getWall(convertX(x, slot), y, direction);
  }

  /**
   * A method used to get the coordinates of the center of a TriGrid of the given size.
   * 
   * @param size  size of a TriGrid whose center coordinates are asked for
   * @return      TriPoint of the center
   */
  public static TriPoint getCenterCords(int size) {
    TriPoint output = new TriPoint();
    if (size%3 == 0) {
      output.y = (size-1) - size/3;
      output.x = (output.y-1) / 2;
      output.slot = Slot.B;
    } else if ((size+1)%3 == 0) {
      output.y = size - (size+1)/3;
      output.x = (output.y-1) / 2;
      output.slot = Slot.B;
    } else if ((size-1)%3 == 0) {
      output.y = size - (size+2)/3;
      output.x = (output.y) / 2;
      output.slot = Slot.A;
    }
    return output;
  }

  /**
   * A getter method for the size of the TriGrid.
   * 
   * @return  size of the TriGrid
   */
  public int getSize() {
    return size;
  }

  /**
   * A method used to determine the index of the slot at the given x coordinate of the Grid.
   * 
   * @param x x coordinate of the slot in the Grid
   * @return  Slot.A if the slots index is A, Slot.B if it isn't
   */
  public static Slot getSlot(int x) {
    return (x%2 == 0 ? Slot.A : Slot.B);
  }

  /**
   * A getter method for the Grid Object.
   * 
   * @return  grid of the TriGrid
   */
  public Grid<T,W> toGrid() {
    return grid;
  }

  /**
   * A method used to convert the Grid of the TriGrid to an Object[][].
   * 
   * @return  grid of the TriGrid as an Object[][]
   */
  public Object[][] toArray() {
    return grid.toArray();
  }

  /**
   * A method used to print the values of all slots of the TriGrid.
   * Formatting only works if every value can be converted into a String with a length of 2.
   */
  public void printCells() {
    StringBuilder outputBuilder = new StringBuilder();
    for (int y = 0; y < size; y++) {
      outputBuilder.append("\n");
      for (int i = 0; i < size-(y+1); i++) {
        outputBuilder.append("  ");
      }
      outputBuilder.append(" ");
      for (int x = 0; x < y+1; x++) {
        outputBuilder.append(Box.slash + Box.bSlash + (hasTwoSlots(x, y) ? get(x, y, Slot.B).toString() : ""));
      }
      outputBuilder.append("\n");
      for (int i = 0; i < size-(y+1); i++) {
        outputBuilder.append("  ");
      }
      for (int x = 0; x < y+1; x++) {
        outputBuilder.append(Ansi.style(Box.slash + get(x, y, Slot.A).toString() + Box.bSlash, Ansi.UNDERLINE));
      }
    }
    System.out.print(outputBuilder.toString());
  }
}