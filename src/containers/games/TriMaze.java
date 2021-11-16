package containers.games;

import characters.Box;
import containers.Direction;
import containers.Slot;
import containers.TriGrid;
import containers.TriPoint;
import characters.*;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

/**
 * A class that generates triangular mazes using the {@link containers.TriGrid} class.
 * @author Leandro Piras
 * @version 1.1
 */
public class TriMaze {
  // the grid all values will be stored in
  private TriGrid<Boolean,Boolean> grid;
  // size of the TriMaze
  private final int size;
  // the seed that will be used to generate the maze
  private final long seed;
  // boolean determining if a given or a random seed is used
  private final boolean useSeed;
  // TriPoint of the start
  private TriPoint start = new TriPoint(0, 0, Slot.A);
  // TriPoint of the end
  private TriPoint end = new TriPoint();

  /**
   * A basic constructor
   * 
   * @param size
   * 
   * @see #TriMaze(int, boolean)
   */
  public TriMaze(int size) {
    this(size, false);
  }

  /**
   * A constructor using the ascii codes of a String as seed.
   * 
   * @param size  int size of the TriMaze
   * @param seed  String seed used to generate the TriMaze
   * 
   * @see #TriMaze(int, long)
   * @see #seedOfString(String)
   */
  public TriMaze(int size, String seed) {
    this(size, seedOfString(seed));
  }

  /**
   * A constructor that generates the TriMaze after the given seed.
   * 
   * @param size  int size of the TriMaze
   * @param seed  long seed used to generate the TriMaze
   * 
   * @see #TriMaze(int, long, boolean)
   */
  public TriMaze(int size, long seed) {
    this(size, seed, false);
  }

  /**
   * A constructor.
   * 
   * @param size      int size of the TriMaze
   * @param debugMode boolean determining wether the TriMaze should be automatically generated or not
   */
  public TriMaze(int size, boolean debugMode) {
    this.size = size;
    this.seed = 0;
    useSeed = false;
    grid = new TriGrid<>(size,true,true);
    if (!debugMode) {
      generate();
    }
  }

  /**
   * A constructor using the ascii codes of a String as seed to generate the TriMaze.
   * 
   * @param size      int size of the TriMaze
   * @param seed      String seed
   * @param debugMode boolean determining wether the TriMaze should be automatically generated or not
   * 
   * @see #TriMaze(int, long, boolean)
   * @see #seedOfString(String)
   */
  public TriMaze(int size, String seed, boolean debugMode) {
    this(size, seedOfString(seed), debugMode);
  }

  /**
   * A constructor using a seed to generate the TriMaze.
   * 
   * @param size      int size of the TriMaze
   * @param seed      long seed
   * @param debugMode boolean determining wether the TriMaze should be automatically generated or not
   */
  public TriMaze(int size, long seed, boolean debugMode) {
    this.size = size;
    this.seed = seed;
    useSeed = true;
    grid = new TriGrid<>(size,true,true);
    if (!debugMode) {
      generate();
    }
  }

  /**
   * A method used to convert the characters of a String to ascii codes for them to be used as a seed.
   * 
   * @param seed  String seed to be converted
   * @return      long converted seed
   */
  private static long seedOfString(String seed) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < seed.length(); i++) {
      sb.append((int) seed.charAt(i));
    }
    return Long.parseLong(sb.toString());
  }

  /**
   * A method used to generate the TriMaze.
   */
  private void generate() {
    Stack<TriPoint> log = new Stack<>();
    ArrayList<TriPoint> visited = new ArrayList<>();
    ArrayList<TriPoint> options = new ArrayList<>();
    Random random;
    if (useSeed) {
      random = new Random(seed);
    } else {
      random = new Random();
    }
    log.add(start);
    int biggestLogSize = 0;
    visited.add(start);
    grid.set(start, false);
    while(log.size() > 0) {
      TriPoint current = log.peek();
      options = findOptions(current);
      if (options.size() == 0) {
        if (log.size() > biggestLogSize) {
          biggestLogSize = log.size();
          end = log.peek();
        }
        log.pop();
        continue;
      }
      TriPoint next = options.get(random.nextInt(options.size()));
      grid.setWall(current, grid.calcDirection(current, next), false);
      grid.set(next, false);
      log.add(next);
      visited.add(next);
    }
  }

  /**
   * A method used by {@link #generate()} to find all possible neighbours of a cell to go to.
   * 
   * @param current TriPoint of current cell
   * @return        ArrayList<Point> containing all possible neighbours
   */
  private ArrayList<TriPoint> findOptions(TriPoint current) {
    ArrayList<TriPoint> options = new ArrayList<>();
    if (grid.areCordsValid(current.x, current.y, current.slot.other())) {
      if (grid.get(current.x, current.y, current.slot.other()).equals(true)) {
        options.add(new TriPoint(current.x, current.y, current.slot.other()));
      }
    }
    if (grid.areCordsValid(current.slot.isA() ? current.x-1 : current.x+1, current.y, current.slot.other())) {
      if (grid.get(current.slot.isA() ? current.x-1 : current.x+1, current.y, current.slot.other()).equals(true)) {
        options.add(new TriPoint(current.slot.isA() ? current.x-1 : current.x+1, current.y, current.slot.other()));
      }
    }
    if (grid.areCordsValid(current.x, current.slot.isA() ? current.y+1 : current.y-1, current.slot.other())) {
      if (grid.get(current.x, current.slot.isA() ? current.y+1 : current.y-1, current.slot.other()).equals(true)) {
        options.add(new TriPoint(current.x, current.slot.isA() ? current.y+1 : current.y-1, current.slot.other()));
      }
    }
    return options;
  }

  /**
   * A method used to print the TriMaze using default values.
   */
  public void print() {
    print("<>", "><", "  ");
  }

  /**
   * A method used to print the TriMaze using the given Strings to represent the start, end and empty cells.
   * 
   * @param startString String used to represent the start (must have a length of 2)
   * @param endString   String used to represent the end (must have a length of 2)
   * @param emptyString String used to represent en empty cell (must have a length of 2)
   * 
   * @throws IllegalArgumentException if the formatting Strings length is not 2
   */
  public void print(String startString, String endString, String emptyString) {
    if (startString.length() != 2 || endString.length() != 2 || emptyString.length() != 2) {
      throw new IllegalArgumentException("The formatting Strings must have a length of 2.");
    }
    String slash = Box.slash;
    String bSlash = Box.bSlash;
    StringBuilder outputBuilder = new StringBuilder();
    for (int y = 0; y < size; y++) {
      System.out.print("\n");
      for (int i = 0; i < size-(y+1); i++) {
        System.out.print("  ");
      }
      System.out.print(" ");
      //  ╱╲xx╱╲xx╱╲
      for (int x = 0; x < y+1; x++) {
        System.out.print((Boolean) grid.getWall(x, y, Slot.A, Direction.LEFT) ? slash : " ");
        System.out.print((Boolean) grid.getWall(x, y, Slot.A, Direction.RIGHT) ? bSlash : " ");
        if (TriGrid.hasTwoSlots(x, y)) {
          TriPoint tpt = new TriPoint(x, y, Slot.B);
          if (tpt.equals(start)) {
            System.out.print(startString);
          } else if (tpt.equals(end)) {
            System.out.print(endString);
          } else {
            System.out.print(emptyString);
          }
        }
      }
      System.out.print("\n");
      for (int i = 0; i < size-(y+1); i++) {
        System.out.print("  ");
      }
      //  ╱xx╲╱xx╲╱xx╲
      for (int x = 0; x < y+1; x++) {
        StringBuilder sb = new StringBuilder();
        sb.append((Boolean) grid.getWall(x, y, Slot.A, Direction.LEFT) ? slash : " ");
        TriPoint tpt = new TriPoint(x, y, Slot.A);
        if (tpt.equals(start)) {
          sb.append(startString);
        } else if (tpt.equals(end)) {
          sb.append(endString);
        } else {
          sb.append(emptyString);
        }
        sb.append((Boolean) grid.getWall(x, y, Slot.A, Direction.RIGHT) ? bSlash : " ");
        if ((Boolean) grid.getWall(x, y, Slot.A, Direction.DOWN)) {
          if (
            !(Boolean) grid.getWall(x, y, Slot.A, Direction.LEFT) && 
            !(Boolean) grid.getWall(x, y, Slot.A, Direction.RIGHT) &&
            emptyString == "  ") {
              System.out.print(Ansi.style(sb.toString().replace(emptyString, "__"), Ansi.UNDERLINE));
          } else {
            System.out.print(Ansi.style(sb.toString(), Ansi.UNDERLINE));
          }
        } else {
          System.out.print(sb.toString());
        }
      }
    }
    System.out.print(outputBuilder.toString());
  }

  /**
   * A method used to print the TriMaze in a compact view.
   * Start and end will be displayed with the "invert" Ansi-escape code.
   */
  public void printCompact() {
    String slash = Box.slash;
    String bSlash = Box.bSlash;
    for (int y = 0; y < size; y++) {
      System.out.print("\n");
      for (int i = 0; i < size-(y+1); i++) {
        System.out.print(" ");
      }
      System.out.print(" ");
      for (int x = 0; x < y+1; x++) {
        boolean leftWall = (Boolean) grid.getWall(x, y, Slot.A, Direction.LEFT);
        boolean rightWall = (Boolean) grid.getWall(x, y, Slot.A, Direction.RIGHT);
        boolean lowerWall = (Boolean) grid.getWall(x, y, Slot.A, Direction.DOWN);
        boolean markA = (x == start.x && y == start.y && start.slot.isA()) || ((x == end.x && y == end.y && end.slot.isA()));
        boolean markNextB = (x == start.x && y == start.y && start.slot.isB()) || ((x == end.x && y == end.y && end.slot.isB()));
        boolean markLastB = (x-1 == start.x && y == start.y && start.slot.isB()) || ((x-1 == end.x && y == end.y && end.slot.isB()));
        if (markA || markLastB) {
          System.out.print(Ansi.style(leftWall ? slash : lowerWall ? "_" : " ", Ansi.INVERT, lowerWall ? Ansi.UNDERLINE : Ansi.NOT_UNDERLINED));
        } else {
          System.out.print(Ansi.style(leftWall ? slash : lowerWall ? "_" : " ", lowerWall ? Ansi.UNDERLINE : Ansi.NOT_UNDERLINED));
        }
        if (markA || markNextB) {
          System.out.print(Ansi.style(rightWall ? bSlash : lowerWall ? "_" : " ", Ansi.INVERT, lowerWall ? Ansi.UNDERLINE : Ansi.NOT_UNDERLINED));
        } else {
          System.out.print(Ansi.style(rightWall ? bSlash : lowerWall ? "_" : " ", lowerWall ? Ansi.UNDERLINE : Ansi.NOT_UNDERLINED));
        }
      }
    }
  }

  /**
   * A getter for the {@link #grid} of the TriGrid.
   * 
   * @return  TriGrid<Boolean, Boolean> {@link #grid}
   */
  public TriGrid<Boolean,Boolean> toTriGrid() {
    return grid;
  }

  /**
   * Returns the {@link #grid} as an Object[][] array.
   * 
   * @return  Object[][] {@link #grid}.toArray()
   */
  public Object[][] toArray() {
    return grid.toArray();
  }

  /**
   * A getter for the start TriPoint
   * 
   * @return  TriPoint {@link #start}
   */
  public TriPoint getStart() {
    return start;
  }

  /**
   * A getter for the end TriPoint
   * 
   * @return  TriPoint {@link #end}
   */
  public TriPoint getEnd() {
    return end;
  }

  /**
   * A getter for the size of the TriMaze.
   * 
   * @return  int {@link #size}
   */
  public int getSize() {
    return size;
  }
}
