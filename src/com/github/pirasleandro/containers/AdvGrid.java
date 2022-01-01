package com.github.pirasleandro.containers;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Extends grid.Grid to allow the user to additionally set the values of the corners
 * @see com.github.pirasleandro.containers.Grid
 * 
 * @param T type of the main storage values
 * @param W type of the wall values
 * @param C type of the corner values
 */
public class AdvGrid<T,W,C> extends Grid<T,W> {

  /**
   * A basic constructor
   * 
   * @param width   width of the grid
   * @param height  height of the grid
   */
  public AdvGrid(int width, int height) {
    super(width, height);
  }

  /**
   * A constructor that additionally sets all values of the grid to the given default values.
   * 
   * @param width         width of the grid
   * @param height        height of the grid
   * @param defaultCell   Object that all cells will be set to
   * @param defaultWall   Object that all walls will be set to
   * @param defaultCorner Object that all corners will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#AdvancedGrid(int, int)
   * @see com.github.pirasleandro.containers.AdvGrid#fill(Object, Object, Object)
   */
  public AdvGrid(int width, int height, T defaultCell, W defaultWall, C defaultCorner) {
    super(width, height);
    fill(defaultCell, defaultWall, defaultCorner);
  }

  /**
   * A method used to set all values of the grid to the given default values
   * 
   * @param defaultCell   Object that all cells will be set to
   * @param defaultWall   Object that all walls will be set to
   * @param defaultCorner Object that all corners will be set to
   */
  public void fill(T defaultCell, W defaultWall, C defaultCorner) {
    super.fill(defaultCell, defaultWall);
    for (int y = 0; y < super.height; y++) {
      for (int x = 0; x < super.width; x++) {
        setCorners(x, y, defaultCorner);
      }
    }
  }

  /**
   * A method used to set all cell and wall values to the given default values
   * 
   * @param defaultCell Object that all cells will be set to
   * @param defaultWall Object that all walls will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#fill(Object, Object, Object)
   */
  public void fill(T defaultCell, W defaultWall) {
    fill(defaultCell, defaultWall, null);
  }

  /**
   * A method used to get the value of the corner in the directions relative to the cell
   * at the given coordinates.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param vDirection  vertical direction from cell to corner
   * @param hDirection  horizontal direciton from cell to corner
   * @return            value of the corner in the directions relative to the cell
   */
  public Object getCorner(int x, int y, Direction vDirection, Direction hDirection) {
    if (vDirection.equals(Direction.UP) && hDirection.equals(Direction.LEFT)) {
      return super.gridArray[convert(y)-1][convert(x)-1]; // upper left
    } else if (vDirection.equals(Direction.UP) && hDirection.equals(Direction.RIGHT)) {
      return super.gridArray[convert(y)-1][convert(x)+1]; // upper right
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.LEFT)) {
      return super.gridArray[convert(y)+1][convert(x)-1]; // lower left
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.RIGHT)){
      return super.gridArray[convert(y)+1][convert(x)+1]; // lower right
    } else {
      return null;
    }
  }

  /**
   * A method used to get the value of the corner at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x x coordinate of the corner
   * @param y y coordinate of the corner
   * @return  Object with the value of the corner
   */
  public Object getCorner(int x, int y) {
    return super.gridArray[y*2][x*2];
  }

  /**
   * A method used to set the value of the corner in the given directions relative to the cell at the
   * given coordinates.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param vDirection  vertical direction from cell to corner
   * @param hDirection  horizontal direction from cell to corner
   * @param value       value that the corner will be set to
   */
  public void setCorner(int x, int y, Direction vDirection, Direction hDirection, C value) {
    if (vDirection == Direction.UP && hDirection == Direction.LEFT) {
      super.gridArray[convert(y)-1][convert(x)-1] = value; // upper left
    } else if (vDirection.equals(Direction.UP) && hDirection.equals(Direction.RIGHT)) {
      super.gridArray[convert(y)-1][convert(x)+1] = value; // upper right
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.LEFT)) {
      super.gridArray[convert(y)+1][convert(x)-1] = value; // lower left
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.RIGHT)){
      super.gridArray[convert(y)+1][convert(x)+1] = value; // lower right
    }
  }

  /**
   * A method used to set the value of the corner at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x     x coordinate of the corner
   * @param y     y coordinate of the corner
   * @param value the value the corner will be set to
   */
  public void setCorner(int x, int y, C value) {
    super.gridArray[y*2][x*2] = value;
  }

  /**
   * A method used to set the values of the corners of the cell at the given coordinates to the given values.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param upperLeft   upper left corner value
   * @param upperRight  upper right corner value
   * @param lowerLeft   lower left corner value
   * @param lowerRight  lower right corner value
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setCorner(int, int, Direction, Direction, Object)
   */
  public void setCorners(int x, int y, C upperLeft, C upperRight, C lowerLeft, C lowerRight) {
    setCorner(x, y, Direction.UP, Direction.LEFT, upperLeft);
    setCorner(x, y, Direction.UP, Direction.RIGHT, upperRight);
    setCorner(x, y, Direction.DOWN, Direction.LEFT, lowerLeft);
    setCorner(x, y, Direction.DOWN, Direction.RIGHT, lowerRight);
  }

  /**
   * A method used to set the values of the corners of the cell at the given coordinates to the given values.
   * 
   * @param x       x coordinate of the cell
   * @param y       y coordinate of the cell
   * @param corners corner-type array containing the four corner values
   * 
   * @throws IllegalArgumentException if the length of the corner array does not equal 4
   */
  public void setCorners(int x, int y, C[] corners) {
    if (corners.length != 4) {
      throw new IllegalArgumentException("the corners array must have a length of 4");
    }
    setCorners(x, y, corners[0], corners[1], corners[2], corners[3]);
  }

  /**
   * A method used to set the values of all corners of the cell at the given coordinates to
   * the given default value.
   * 
   * @param x       x coordinate of the cell
   * @param y       y coordinate of the cell
   * @param corners value the corners will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setCorners(int, int, Object, Object, Object, Object)
   */
  public void setCorners(int x, int y, C defaultCorner) {
    setCorners(x, y, defaultCorner, defaultCorner, defaultCorner, defaultCorner);
  }

  /**
   * A method used to get the value of the cell in the given directions relative to the corner
   * at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x           x coordinate of the corner
   * @param y           y coordinate of the corner
   * @param vDirection  vertical direction from corner to cell
   * @param hDirection  horizontal direction from corner to cell
   * @return            Object with the value of the cell
   */
  public Object getFromCorner(int x, int y, Direction vDirection, Direction hDirection) {
    if (vDirection.equals(Direction.UP) && hDirection.equals(Direction.LEFT)) {
      return super.gridArray[(y*2)-1][(x*2)+1]; // upper left
    } else if (vDirection.equals(Direction.UP) && hDirection.equals(Direction.RIGHT)) {
      return super.gridArray[(y*2)-1][(x*2)+1]; // upper right
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.LEFT)) {
      return super.gridArray[(y*2)+1][(x*2)-1]; // lower left
    } else if (vDirection.equals(Direction.DOWN) && hDirection.equals(Direction.RIGHT)){
      return super.gridArray[(y*2)+1][(x*2)+1]; // lower right
    } else {
      return null;
    }
  }

  /**
   * A method used to get the value of the wall in the given direction relative to the corner
   * at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x         x coordinate of the corner
   * @param y         y coordinate of the corner
   * @param direction direction from corner to wall
   * @return          Object with the value of the wall
   */
  public Object getWallFromCorner(int x, int y, Direction direction) {
    switch (direction) {
      case UP: return super.gridArray[(y*2)-1][x*2];
      case DOWN: return super.gridArray[(y*2)+1][x*2];
      case LEFT: return super.gridArray[y*2][(x*2)-1];
      case RIGHT: return super.gridArray[y*2][(x*2)+1];
      default: throw new EnumConstantNotPresentException(Direction.class, direction.toString());
    }
  }

  /**
   * A method used to set the value of the wall in the given direction relative to the corner
   * at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x         x coordinate of the corner
   * @param y         y coordinate of the corner
   * @param direction direction from corner to wall
   * @param value     value that the wall will be set to
   */
  public void setWallFromCorner(int x, int y, Direction direction, W value) {
    switch (direction) {
      case UP: super.gridArray[(y*2)-1][x*2] = value; break;
      case DOWN: super.gridArray[(y*2)+1][x*2] = value; break;
      case LEFT: super.gridArray[y*2][(x*2)-1] = value; break;
      case RIGHT: super.gridArray[y*2][(x*2)+1] = value; break;
      default: throw new EnumConstantNotPresentException(Direction.class, direction.toString());
    }
  }

  /**
   * A method used to set the values of the walls of the corner at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x         x coordinate of the corner
   * @param y         y coordinate of the corner
   * @param upperWall upper wall value
   * @param lowerWall lower wall value
   * @param leftWall  left wall value
   * @param rightWall right wall value
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWallFromCorner(int, int, Direction, Object)
   */
  public void setWallsFromCorner(int x, int y, W upperWall, W lowerWall, W leftWall, W rightWall) {
    setWallFromCorner(x, y, Direction.UP, upperWall);
    setWallFromCorner(x, y, Direction.DOWN, lowerWall);
    setWallFromCorner(x, y, Direction.LEFT, leftWall);
    setWallFromCorner(x, y, Direction.RIGHT, rightWall);
  }

  /**
   * A method used to set all values of the walls of the corner at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x           x coordinate of the corner
   * @param y           y coordinate of the corner
   * @param defaultWall value the walls will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWallsFromCorner(int, int, Object, Object, Object, Object)
   */
  public void setWallsFromCorner(int x, int y, W defaultWall) {
    setWallsFromCorner(x, y, defaultWall, defaultWall, defaultWall, defaultWall);
  }

  /**
   * A method used to set the values of the walls of the corner at the given coordinates.
   * Corner coordinates are equal to their lower-right cells coordinates.
   * 
   * @param x     x coordinate of the corner
   * @param y     y coordinate of the corner
   * @param walls wall-type array containing the four wall values
   * 
   * @throws IllegalArgumentException if the length of the wall values does not equal 4
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWallsFromCorner(int, int, Object, Object, Object, Object)
   */
  public void setWallsFromCorner(int x, int y, W[] walls) {
    if (walls.length != 4) {
      throw new IllegalArgumentException("The length of the walls array must be 4");
    }
    setWallsFromCorner(x, y, walls[0], walls[1], walls[2], walls[3]);
  }

  /**
   * A method used to set the value of the wall and the value of their adjacent corners
   * in the direction relative to the cell at the given coordinates.
   * 
   * Example: if the direction is Direction.UP, the upper wall value, the upper right and
   * the upper left corner value will be set to the given values.
   * 
   * @param x             x coordinate of the cell
   * @param y             y coordinate of the cell
   * @param direction     direction from cell to wall
   * @param wallValue     value that the wall will be set to
   * @param cornerValue   value that the corner clockwise from the direction will be set to
   * @param cornerValue2  value that the corner counterclockwise from the direction will be set to
   */
  public void setWallAndCorners(int x, int y, Direction direction, W wallValue, C cornerValue, C cornerValue2) {
    setWall(x, y, direction, wallValue);
    if (direction.isVertical()) {
      setCorner(x, y, direction, direction.turnClockwise(), cornerValue);
      setCorner(x, y, direction, direction.turnCounterClockwise(), cornerValue);
    } else {
      setCorner(x, y, direction.turnClockwise(), direction, cornerValue);
      setCorner(x, y, direction.turnCounterClockwise(), direction, cornerValue);
    }
  }

  /**
   * A method used to set all the wall and corner values of the given cell to the given default values.
   * 
   * @param x             x coordinate of the cell
   * @param y             y coordinate of the cell
   * @param defaultWall   value the walls will be set to
   * @param defaultCorner value the corners will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWalls(int, int, Object)
   * @see com.github.pirasleandro.containers.AdvGrid#setCorners(int, int, Object)
   */
  public void setWallsAndCorners(int x, int y, W defaultWall, C defaultCorner) {
    setWalls(x, y, defaultWall);
    setCorners(x, y, defaultCorner);
  }

  /**
   * A method used to set the wall and corner values of the cell at the given coordinates to the given values.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param upperWall   upper wall value
   * @param lowerWall   lower wall value
   * @param leftWall    left wall value
   * @param rightWall   right wall value
   * @param upperLeft   upper left corner value
   * @param upperRight  upper right corner value
   * @param lowerLeft   lower left corner value
   * @param lowerRight  lower right corner value
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWalls(int, int, Object, Object, Object, Object)
   * @see com.github.pirasleandro.containers.AdvGrid#setCorners(int, int, Object, Object, Object, Object)
   */
  public void setWallsAndCorners(int x, int y, W upperWall, W lowerWall, W leftWall, W rightWall, C upperLeft, C upperRight, C lowerLeft, C lowerRight) {
    setWalls(x, y, upperWall, lowerWall, leftWall, rightWall);
    setCorners(x, y, upperLeft, upperRight, lowerLeft, lowerRight);
  }

  /**
   * A method used to set the wall and corner values of the cell at the given coordinates to the given values.
   * 
   * @param x       x coordinate of the cell
   * @param y       y coordinate of the cell
   * @param walls   wall-type array containing the four wall values
   * @param corners corner-type array containing the four corner values
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWalls(int, int, Object[])
   * @see com.github.pirasleandro.containers.AdvGrid#setCorners(int, int, Object[])
   */
  public void setWallsAndCorners(int x, int y, W[] walls, C[] corners) {
    setWalls(x, y, walls);
    setCorners(x, y, corners);
  }

  /**
   * A method used to set the value, wall values and corner values of the cell at the given coordinates
   * to the given values.
   * 
   * @param x           x coordinate of the cell
   * @param y           y coordinate of the cell
   * @param value       value the cell will be set to
   * @param upperWall   upper wall value
   * @param lowerWall   lower wall value
   * @param leftWall    left wall value
   * @param rightWall   right wall value
   * @param upperLeft   upper left corner value
   * @param upperRight  upper right corner value
   * @param lowerLeft   lower left corner value
   * @param lowerRight  lower right corner value
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWallsAndCorners(int, int, Object, Object, Object, Object, Object, Object, Object, Object)
   * @see com.github.pirasleandro.containers.AdvGrid#set(int, int, Object)
   */
  public void setWallValueAndCorners(int x, int y, T value, W upperWall, W lowerWall, W leftWall, W rightWall, C upperLeft, C upperRight, C lowerLeft, C lowerRight) {
    setWallsAndCorners(x, y, upperWall, lowerWall, leftWall, rightWall, upperLeft, upperRight, lowerLeft, lowerRight);
    set(x, y, value);
  }

  /**
   * A method used to set the value, wall values and corner values of the cell at the given coordinates
   * to the given values.
   * 
   * @param x       x coordinate of the cell
   * @param y       y coordinate of the cell
   * @param value   value the cell will be set to
   * @param walls   wall-type array containing the four wall values
   * @param corners corner-type array containing the four corner values
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setWallsAndCorners(int, int, Object[], Object[])
   * @see com.github.pirasleandro.containers.AdvGrid#set(int, int, Object)
   */
  public void setWallValueAndCorners(int x, int y, T value, W[] walls, C[] corners) {
    setWallsAndCorners(x, y, walls, corners);
    set(x, y, value);
  }

  /**
   * A method used to set the values of all corners at the border of the grid to the given default value.
   * Corners count as border if they have less than four adjacent walls.
   * 
   * @param defaultCorner value the corners will be set to
   */
  public void setBorderCorners(C defaultCorner) {
    for (int x = 0; x < width; x++) {
      setCorner(x, 0, Direction.UP, Direction.LEFT, defaultCorner);
      setCorner(x, 0, Direction.UP, Direction.RIGHT, defaultCorner);
      setCorner(x, height-1, Direction.DOWN, Direction.LEFT, defaultCorner);
      setCorner(x, height-1, Direction.DOWN, Direction.RIGHT, defaultCorner);
    }
    for (int y = 0; y < height; y++) {
      setCorner(0, y, Direction.UP, Direction.LEFT, defaultCorner);
      setCorner(0, y, Direction.DOWN, Direction.LEFT, defaultCorner);
      setCorner(width-1, y, Direction.UP, Direction.RIGHT, defaultCorner);
      setCorner(width-1, y, Direction.DOWN , Direction.RIGHT, defaultCorner);
    }
  }

  /**
   * A method used to set the values of the walls and corners at the border to the given default values.
   * Walls count as border if they have only one adjacent cell, Corners only count as border if they have
   * less than four adjacent walls.
   * 
   * @param defaultWall   value that the walls will be set to
   * @param defaultCorner value that the corners will be set to
   * 
   * @see com.github.pirasleandro.containers.AdvGrid#setBorderCorners(Object)
   */
  public void setBorder(W defaultWall, C defaultCorner) {
    super.setBorder(defaultWall);
    setBorderCorners(defaultCorner);
  }
}
