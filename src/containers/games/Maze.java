package containers.games;

import characters.*;
import containers.AdvGrid;
import containers.Direction;
import containers.Grid;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import java.awt.Point;

/**
 * @author Leandro Piras
 * @version 1.1
 * 
 * Class used to generate and print mazes.
 */
public class Maze {
  // grid in which all values representing the maze will be stored in
  private AdvGrid<Boolean,Boolean,Boolean> grid;

  // width and height of the maze
  protected int width, height;
  
  // point of start and end
  protected Point start, end;

  /**
   * A constructor.
   * 
   * @param width   width of the maze
   * @param height  height of the maze
   */
  public Maze(int width, int height) {
    this.width = width;
    this.height = height;
    this.grid = new AdvGrid<>(width, height, true, true, true);
    generate();
  }

  /**
   * A method used to generate the maze
   */
  private void generate() {
    Stack<Point> log = new Stack<>();
    ArrayList<Point> visited = new ArrayList<>();
    ArrayList<Point> options = new ArrayList<>();
    Random random = new Random();
    start = new Point(0,0);
    log.add(start);
    int biggestLogSize = 0;
    visited.add(start);
    grid.set(start, false);
    while(log.size() > 0) {
      Point current = log.peek();
      options = findOptions(current);
      if (options.size() == 0) {
        if (log.size() > biggestLogSize) {
          biggestLogSize = log.size();
          end = log.peek();
        }
        log.pop();
        continue;
      }
      Point next = options.get(random.nextInt(options.size()));
      grid.setWall(current.x, current.y, grid.calcDirection(current.x, current.y, next.x, next.y), false);
      grid.set(next, false);
      log.add(next);
      visited.add(next);
    }
  }

  /**
   * A method used in {@link #generate()} to find the cursors available options for the next cell.
   * 
   * @param current Point of the cell whose options are asked for
   * @return        ArrayList<Point> of all options
   */
  private ArrayList<Point> findOptions(Point current) {
    ArrayList<Point> options = new ArrayList<>();
    if (grid.areCordsValid(current.x+1, current.y)) {
      if (grid.get(current.x+1, current.y).equals(true)) {
        options.add(new Point(current.x+1, current.y));
      }
    }
    if (grid.areCordsValid(current.x, current.y+1)) {
      if (grid.get(current.x, current.y+1).equals(true)) {
        options.add(new Point(current.x, current.y+1));
      }
    }
    if (grid.areCordsValid(current.x-1, current.y)) {
      if (grid.get(current.x-1, current.y).equals(true)) {
        options.add(new Point(current.x-1, current.y));
      }
    }
    if (grid.areCordsValid(current.x, current.y-1)) {
      if (grid.get(current.x, current.y-1).equals(true)) {
        options.add(new Point(current.x, current.y-1));
      }
    }
    return options;
  }

  /**
   * A getter method for {@link #grid}
   * 
   * @return  {@link #grid}
   */
  public AdvGrid<Boolean,Boolean,Boolean> toGrid() {
    return grid;
  }

  /**
   * A method used to convert the {@link #grid} to an int[][].
   * 0 = empty, 1 = wall
   * 
   * @return
   * 
   * @see containers.games.Maze#toIntArray(boolean, boolean)
   */
  public int[][] toIntArray() {
    return toIntArray(false, false);
  }

  /**
   * A method used to convert the {@link #grid} to an int[][].
   * 0 = empty, 1 = wall. Depending on the two boolean arguments, there will also be 2 = start, 3 = end.
   * 
   * @param markStart boolean to determine if the start point should be marked
   * @param markEnd   boolean to determine if the end point should be marked
   * @return          an int[][] filled with 0s and 1s. (depending on the boolean arguments also 3s and 4s)
   */
  public int[][] toIntArray(boolean markStart, boolean markEnd) {
    Object[][] gridArray = grid.toArray();
    int[][] output = new int[gridArray.length][gridArray[0].length];
    for (int y = 0; y < gridArray.length; y++) {
      for (int x = 0; x < gridArray[0].length; x++) {
        if (x == Grid.convert(start.x) && y == Grid.convert(start.y) && markStart) {
          output[y][x] = 2;
        } else if (x == Grid.convert(end.x) && y == Grid.convert(end.y) && markEnd) {
          output[y][x] = 3;
        } else {
          output[y][x] = (gridArray[y][x].equals(true) ? 1 : 0);
        }
      }
    }
    return output;
  }

  /**
   * A method used to convert the maze into a String. Walls, empty spaces, new line,
   * the start and end point will be represented by the given Strings.
   * 
   * @param empty     String used for empty spaces
   * @param wall      String used for walls
   * @param start     String used to mark the start point
   * @param end       String used to mark the end point
   * @param nextLine  String used for new lines ("\n" recommended)
   * @return          String of converted maze
   */
  public String toString(String empty, String wall, String start, String end, String nextLine) {
    StringBuilder outputBuilder = new StringBuilder();
    int[][] gridArray = toIntArray(true, true);
    for (int y = 0; y < gridArray.length; y++) {
      outputBuilder.append(nextLine);
      for (int x = 0; x < gridArray[0].length; x++) {
        switch (gridArray[y][x]) {
          case 0 -> outputBuilder.append(empty);
          case 1 -> outputBuilder.append(wall);
          case 2 -> outputBuilder.append(start);
          case 3 -> outputBuilder.append(end);
          default -> outputBuilder.append("!!");
        }
      }
    }
    return outputBuilder.toString();
  }

  /**
   * A submethod of {@link Maze#toString(String, String, String, String, String)} to only customize
   * emtpy spaces, walls, next line.
   * 
   * @param empty     String used for empty spaces
   * @param wall      String used for walls
   * @param nextLine  String used for new lines ("\n" recommended)
   * @return          String of converted maze
   * 
   * @see containers.games.Maze#toString(String, String, String, String, String)
   */
  public String toString(String empty, String wall, String nextLine) {
    return toString(empty, wall, "  ", "  ", nextLine);
  }

  /**
   * A submethod of {@link Maze#toString(String, String, String, String, String)} to only customize
   * emtpy spaces, walls, the start and end point.
   * 
   * @param empty     String used for empty spaces
   * @param wall      String used for walls
   * @param start     String used to mark the start point
   * @param end       String used to mark the end point
   * @return          String of converted maze
   * 
   * @see containers.games.Maze#toString(String, String, String, String, String)
   */
  public String toString(String empty, String wall, String start, String end) {
    return toString(empty, wall, start, end, "\n");
  }

  /**
   * A submethod of {@link Maze#toString(String, String, String, String, String)} to only customize
   * empty spaces, walls.
   * 
   * @param empty     String used for empty spaces
   * @param wall      String used for walls
   * @return
   * 
   * @see containers.games.Maze#toString(String, String, String, String, String)
   * @see containers.games.Maze#toString(String, String, String, String)
   */
  public String toString(String empty, String wall) {
    return toString(empty, wall, "  ", "  ");
  }

  /**
   * A submethod of {@link Maze#toString(String, String, String, String, String)} replacing all
   * arguments with default values.
   * 
   * @see containers.games.Maze#toString(String, String, String, String, String)
   * @see containers.games.Maze#toString(String, String, String, String)
   * @see containers.games.Maze#toString(String, String)
   */
  public String toString() {
    return toString("  ", Block.f.repeat(2));
  }

  public Boolean get(int x, int y) {
    return (Boolean) grid.get(x, y);
  }
  public Boolean getFromCorner(int x, int y, Direction vDirection, Direction hDirection) {
    return (Boolean) grid.getFromCorner(x, y, vDirection, hDirection);
  }
  public Boolean getWall(int x, int y, Direction direction) {
    return (Boolean) grid.getWall(x, y, direction);
  }
  public Boolean getWallFromCorner(int x, int y, Direction direction) {
    return (Boolean) grid.getWallFromCorner(x, y, direction);
  }
  public Boolean getCorner(int x, int y, Direction vDirection, Direction hDirection) {
    return (Boolean) grid.getCorner(x, y, vDirection, hDirection);
  }
  public Boolean getCorner(int x, int y) {
    return (Boolean) grid.getCorner(x, y);
  }
  public Point getStart() {
    return start;
  }
  public Point getEnd() {
    return end;
  }
}
