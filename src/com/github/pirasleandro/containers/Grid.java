package com.github.pirasleandro.containers;

import java.awt.Point;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Storage class used to store normal and wall values in a grid-like structure.
 * The coordinates will refer to the normal values, walls are relative to cells.
 * 
 * @param T type of main storage values
 * @param W type of wall values
 */
public class Grid<T, W> {
  /**
   * Width and height of the grid.
   */
  protected int width, height;
  /**
   * Object[][] where all values will be stored in.
   */
  protected Object[][] gridArray;

  /**
   * Empty constructor. Private to prevent empty Grid objects.
   */
  @SuppressWarnings("unused")
  private Grid() {
  }

  /**
   * A basic constructor.
   * 
   * @param width   width of grid
   * @param height  height of grid
   */
  public Grid(int width, int height) {
    this.width = width;
    this.height = height;
    this.gridArray = new Object[convert(height)][convert(width)];
  }

  /**
   * A constructor that additionally sets all values of the grid to the given default values.
   * 
   * @param width       width of grid
   * @param height      height of grid
   * @param defaultCell Object that all cells will be set to
   * @param defaultWall Object that all walls will be set to
   * 
   * @see com.github.pirasleandro.containers.Grid#Grid(int, int)
   * @see com.github.pirasleandro.containers.Grid#fill(Object, Object)
   */
  public Grid(int width, int height, T defaultCell, W defaultWall) {
    this(width, height);
    fill(defaultCell, defaultWall);
  }

  /**
   * A method used to set all cells and walls to the given default value.
   * 
   * @param defaultCell Object that all cells will be set to
   * @param defaultWall Object that all walls will be set to
   */
  public void fill(T defaultCell, W defaultWall) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        set(x, y, defaultCell);
        setWalls(x, y, defaultWall);
      }
    }
  }

  /**
   * A method used to set the value of a cell at the given coordinates to the given value.
   * 
   * @param x     x coordinate of the cell that will be set
   * @param y     y coordinate of the cell that will be set
   * @param value value that the cell will be set to
   */
  public void set(int x, int y, T value) {
    gridArray[convert(y)][convert(x)] = value;
  }

  /**
   * A method used to set the value of a cell at the given coordinates to the given value.
   * 
   * @param point coordinates of the cell that will be set
   * @param value value that the cell will be set to
   * 
   * @see com.github.pirasleandro.containers.Grid#set(int, int, Object)
   */
  public void set(Point point, T value) {
    set(point.x, point.y, value);
  }

  /**
   * A method used to set the value of a wall relative to the cell at the given coordinates to the given value.
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param direction direction of the wall relative to the cell
   * @param value     value that the wall will be set to
   */
  public void setWall(int x, int y, Direction direction, W value) {
    switch (direction) {
      case UP: gridArray[convert(y)-1][convert(x)] = value; break;
      case DOWN: gridArray[convert(y)+1][convert(x)] = value; break;
      case LEFT: gridArray[convert(y)][convert(x)-1] = value; break;
      case RIGHT: gridArray[convert(y)][convert(x)+1] = value; break;
      default: return;
    }
  }

  /**
   * A method used to set the values of the walls of the cell at the given coordinate to the given values.
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param upperWall value that the upper wall will be set to
   * @param lowerWall value that the lower wall will be set to
   * @param leftWall  value that the left wall will be set to
   * @param rightWall value that the right wall will be set to
   * 
   * @see com.github.pirasleandro.containers.Grid#setWall(int, int, Direction, Object)
   */
  public void setWalls(int x, int y, W upperWall, W lowerWall, W leftWall, W rightWall) {
    setWall(x, y, Direction.UP, upperWall);
    setWall(x, y, Direction.DOWN, lowerWall);
    setWall(x, y, Direction.LEFT, leftWall);
    setWall(x, y, Direction.RIGHT, rightWall);
  }

  /**
   * A method used to set the values of the walls of the cell at the given coordinate to the given values.
   * 
   * @param x     x coordinate of the cell
   * @param y     y coordinate of the cell
   * @param walls wall-type array containing the four wall values
   * 
   * @see com.github.pirasleandro.containers.Grid#setWalls(int, int, Object, Object, Object, Object)
   * 
   * @throws IllegalArgumentException if the length of the wall array does not equal 4
   */
  public void setWalls(int x, int y, W[] walls) {
    if (walls.length != 4) {
      throw new IllegalArgumentException("the walls array must have a length of 4");
    }
    setWalls(x, y, walls[0], walls[1], walls[2], walls[3]);
  }

  /**
   * A method used to set all values of the walls of the cell at the given coordinates to the given value.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param defaultWall value that the walls will be set to
   * 
   * @see com.github.pirasleandro.containers.Grid#setWalls(int, int, Object, Object, Object, Object)
   */
  public void setWalls(int x, int y, W defaultWall) {
    setWalls(x, y, defaultWall, defaultWall, defaultWall, defaultWall);
  }

  /**
   * A method used to set the border of the grid to the given value.
   * The border are all the walls that are only adjacent to one cell.
   * 
   * @param defaultWall value that the walls will be set to
   */
  public void setBorder(W defaultWall) {
    for (int x = 0; x < width; x++) {
      setWall(x, 0, Direction.UP, defaultWall);
      setWall(x, height-1, Direction.DOWN, defaultWall);
    }
    for (int y = 0; y < height; y++) {
      setWall(0, y, Direction.LEFT, defaultWall);
      setWall(width-1, y, Direction.RIGHT, defaultWall);
    }
  }

  /**
   * A method used to calculate the direction that the cell at the second coordinates
   * relative to the first coordinates is.
   * 
   * @param x1  x coordinate of first cell
   * @param y1  y coordinate of first cell
   * @param x2  x coordinate of second cell
   * @param y2  x coordinate of second cell
   * @return    the direction of the second cell relative to the first cell
   */
  public Direction calcDirection(int x1, int y1, int x2, int y2) {
    if (y1 != y2 && x1 != x2) {
      throw new IllegalArgumentException("The coordinates " + x1 + "/" + y1 + " and " + x2 + "/" + y2 + " are not neighbours.");
    }
    if (y1 != y2) {
      if (y1 > y2) {
        return Direction.UP;
      } else {
        return Direction.DOWN;
      }
    } else {
      if (x1 > x2) {
        return Direction.LEFT;
      } else {
        return Direction.RIGHT;
      }
    }
  }

  /**
   * A method used to calculate the direction that the cell at the second point
   * relative to the first point is.
   * 
   * @param pt1 point of first cell
   * @param pt2 point of second cell
   * @return    direction of second cell relative to first cell
   * 
   * @see com.github.pirasleandro.containers.Grid#calcDirection(int, int, int, int)
   */
  public Direction calcDirection(Point pt1, Point pt2) {
    return calcDirection(pt1.x, pt1.y, pt2.x, pt2.y);
  }

  /**
   * A method used to convert from userfriendly coordinates to coordinates in the gridArray.
   * Userfriendly coordinates refer to the cells only, while gridArray coordinates refer the index
   * in the actual array.
   * 
   * @param i coordinate to be converted
   * @return  converted coordinate
   */
  public static int convert(int i) {
    return (i*2)+1;
  }

  /**
   * A method used to convert both coordinates of a point from userfriendly coordinates
   * to gridArray coordinates.
   * 
   * @param point point whose coordinates are to be converted
   * @return      point with converted coordinates
   * 
   * @see com.github.pirasleandro.containers.Grid#convert(int)
   */
  public static Point convert(Point point) {
    point.x = convert(point.x);
    point.y = convert(point.y);
    return point;
  }

  /**
   * A method used to convert from gridArray coordinates to userfriendly coordinates.
   * Opposite of @see grid.Grid#convert(int)
   * 
   * @param i coordinate to be converted
   * @return  converted coordinate
   * 
   * @see com.github.pirasleandro.containers.Grid#convert(int)
   */
  public static int convertBack(int i) {
    return (i-1)/2;
  }

  /**
   * A method used to convert the coordinates of a point from gridArray coordinates to 
   * userfriendly coordinates.
   * 
   * @param point point whose coordinates are to be converted
   * @return      point with converted coordinates
   * 
   * @see com.github.pirasleandro.containers.Grid#convert(Point)
   * @see com.github.pirasleandro.containers.Grid#convertBack(int)
   */
  public static Point convertBack(Point point) {
    point.x = convertBack(point.x);
    point.y = convertBack(point.y);
    return point;
  }

  /**
   * A method used to convert the grid to an Object array.
   * 
   * @return  Object[][] gridArray
   */
  public Object[][] toArray() {
    return gridArray;
  }

  /**
   * A method used to get an Object array containing only the cell values.
   * 
   * @return  Object[][] containing the cell values of the grid
   */
  public Object[][] getCells() {
    Object[][] output = new Object[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        output[y][x] = get(y, x);
      }
    }
    return output;
  }

  /**
   * getter
   * 
   * @return  int height of grid
   */
  public int getHeight() {
    return height;
  }

  /**
   * getter
   * 
   * @return int width of grid
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns the cell value of the given coordinates.
   * 
   * @param x x coordinate of cell
   * @param y y coordinate of cell
   * @return  value of the cell
   */
  public Object get(int x, int y) {
    return gridArray[convert(y)][convert(x)];
  }

  /**
   * Returns the cell value of the given point.
   * 
   * @param point point of the cell
   * @return      value of the cell
   * 
   * @see com.github.pirasleandro.containers.Grid#get(int, int)
   */
  public Object get(Point point) {
    return get(point.x, point.y);
  }

  /**
   * A method used to get the wall in the given direction relative to the cell at the given coordinates.
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param direction direction of the wall relative to cell
   * @return          value of the wall in the direction relative to the cell
   */
  public Object getWall(int x, int y, Direction direction) {
    switch (direction) {
      case UP: return gridArray[convert(y)-1][convert(x)];
      case DOWN: return gridArray[convert(y)+1][convert(x)];
      case LEFT: return gridArray[convert(y)][convert(x)-1];
      case RIGHT: return gridArray[convert(y)][convert(x)+1];
      default: return null;
    }
  }

  /**
   * THIS IS NOT @see grid.Grid#get(Point). This method will return the Object at the given
   * coordinates in the gridArray. This can be a cell, a wall or null.
   * 
   * @param x x coordinate of the Object
   * @param y y coordinate of the Object
   * @return  Object from the gridArray at the given index
   */
  public Object getObject(int x, int y) {
    return gridArray[y][x];
  }

  /**
   * A method used to determine if the coordinates are in the bounds of the grid.
   * 
   * @param x
   * @param y
   * @return
   */
  public boolean areCordsValid(int x, int y) {
    return (x >= 0 && x < width && y >= 0 && y < height);
  }

  /**
   * A method used to determine if the Point is in the bounds of the grid.
   * 
   * @param point
   * @return
   * 
   * @see com.github.pirasleandro.containers.Grid#areCordsValid(int, int)
   */
  public boolean areCordsValid(Point point) {
    return areCordsValid(point.x, point.y);
  }

  /**
   * A method used to calculate if the Object at the given coordinates are a cell Object or not.
   * 
   * @param y y coordinate of the Object
   * @param x x coordinate of the Object
   * @return  true if the Object is a cell, false if it's not
   */ 
  public static boolean isCell(int x, int y) {
    if (y%2 > 0 && x%2 > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method used to calculate if the Object at the given coordinates are a wall Object or not.
   * 
   * @param y y coordinate of the Object
   * @param x x coordinate of the Object
   * @return  true if the Object is a wall, false if it's not
   */
  public static boolean isWall(int x, int y) {
    if ((y%2 > 0 && x%2 == 0) || (y%2 == 0 && x%2 > 0)) {
      return true;
    } 
    return false;
  }

  /**
   * A method used to calculate if the Object at the given coordinates is a vertical wall or not.
   * Walls count as vertical if they are at the left/right of a cell
   * 
   * @param x x coordinate of the wall
   * @param y y coordinate of the wall
   * @return  true if the Object is a vertical wall, false if it's not
   * 
   * @throws IllegalArgumentException if the Object at the coordinates is not a wall
   */
  public static boolean isVWall(int x, int y) {
    if (!isWall(x, y)) {
      throw new IllegalArgumentException("The Object at the coordinates " + x + "/" + y + " is not a wall.");
    }
    if (y%2 > 0 && x%2 == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method used to calculate if the Object at the given coordinates is a horizontal wall or not.
   * Walls count as horizontal if they are at the top/bottom of a cell
   * 
   * @param x x coordinate of the wall
   * @param y y coordinate of the wall
   * @return  true if the Object is a horizontal wall, false if it's not
   * 
   * @throws IllegalArgumentException if the Object at the coordinates is not a wall
   */
  public static boolean isHWall(int x, int y) {
    if (!isWall(x, y)) {
      throw new IllegalArgumentException("The Object at the coordinates " + x + "/" + y + " is not a wall.");
    }
    if (y%2 == 0 && x%2 > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * A method used to calculate if the Object at the given coordinates is a corner or not.
   * A corner is an index in the array that is neither a cell nor a wall.
   * 
   * @param x x coordinate of the Object
   * @param y y coordinate of the Object
   * @return  true if the Object is a corner, false if it's not
   */
  public static boolean isCorner(int x, int y) {
    return (y%2 == 0 && x%2 == 0);
  }
}
