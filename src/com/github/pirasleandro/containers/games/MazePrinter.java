package com.github.pirasleandro.containers.games;

import com.github.pirasleandro.characters.Block;
import com.github.pirasleandro.characters.Box;
import com.github.pirasleandro.containers.Direction;

public class MazePrinter {
  private Maze maze;
  private final int width, height;

  @SuppressWarnings("unused")
  private MazePrinter() {
    width = 0;
    height = 0;
  }

  public MazePrinter(Maze maze) {
    this.maze = maze;
    this.width = maze.width;
    this.height = maze.height;
  }

  /**
   * A method used to print the maze in a compact view with block element characters.
   */
  public void printSmall() {
    for (int y = 0; y < height; y++) {
      System.out.print("\n");
      StringBuilder sb = new StringBuilder();
      for (int x = 0; x < width; x++) {
        sb = new StringBuilder();
        sb.append((Boolean) maze.getCorner(x, y) ? "1" : "0");
        sb.append((Boolean) maze.getWall(x, y, Direction.UP) ? "1" : "0");
        sb.append((Boolean) maze.getWall(x, y, Direction.LEFT) ? "1" : "0");
        sb.append((Boolean) maze.get(x, y) ? "1" : "0");
        System.out.print(Block.getQsOf(sb.toString()));
        if (x+1 == width) {
          sb = new StringBuilder();
          sb.append((Boolean) maze.getCorner(width, y) ? "10" : "00");
          sb.append((Boolean) maze.getWall(x, y, Direction.RIGHT) ? "10" : "00");
          System.out.print(Block.getQsOf(sb.toString()));
        }
      }
      if (y+1 == height) {
        System.out.print("\n");
        sb = new StringBuilder();
        for (int x = 0; x < width; x++) {
          sb.append((Boolean) maze.getCorner(x, height) ? "1" : "0");
          sb.append((Boolean) maze.getWall(x, y, Direction.DOWN) ? "1" : "0");
          sb.append("00");
          System.out.print(Block.getQsOf(sb.toString()));
          if (x+1 == width) {
            System.out.print(Block.getQsOf((Boolean) maze.getCorner(width, height) ? "1000" : "0000"));
          }
        }
      }
    }
  }
  String[] complexFormat = {
      "  "       , Box.r   + Box.h, Box.l   + " ", Box.h  + Box.h,
      Box.d + " ", Box.d_r + Box.h, Box.d_l + " ", Box.dh + Box.h,
      Box.u + " ", Box.u_r + Box.h, Box.u_l + " ", Box.uh + Box.h,
      Box.v + " ", Box.vr  + Box.h, Box.vl  + " ", Box.vh + Box.h
    };
  String test = Box.uh;
  /**
   * A complex method used to print the maze using a few Strings and a String[] as format.
   * The format String[] must have a length of 16 and contain a String for all possible
   * cases of walls and corners around a cell. The index of every String converted to a binary
   * number with four slots, represents the case the String will represent. From left to right
   * the digit is either 1 (wall presetn) or 0 (no wall present) for the upper wall, lower wall,
   * left wall, right wall.
   * <p><b>Example for a single <code>String</code>:</b></p>
   * The index 5 would be 0101, which means the String at the index 5 (in this example "┌─")
   * will be used if there is a lower wall and a right wall, but no upper or left wall.
   * <pre>
   * visualized:
   *            [upper wall]
   *                 ░░
   * [left wall] ░░ "┌─" ▓▓ [right wall]
   *                 ▓▓
   *            [lower wall]
   * </pre>
   * <p><b>Example for a format <code>String[]</code>:</b></p>
   * <pre>{@code
   * String[] format = {
   *  "  ", "╶─", "╴ ", "──",
   *  "╷ ", "┌─", "┐ ", "┬─",
   *  "╵ ", "└─", "┘ ", "┴─",
   *  "│ ", "├─", "┤ ", "┼─"
   * }</pre>
   * 
   * @param startString String the start will be represented by
   * @param endString   String the end will be represented by
   * @param emptyString String an empty cell will be represented by (neither start nor end)
   * @param format      String[] containing the Strings for all possible wall combinations
   * 
   * @throws IllegalArgumentException if the format isn't valid.
   * 
   * @see com.github.pirasleandro.containers.games.MazePrinter#validateFormat(String, String, String, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatCorner(int, int, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatWallFromCorner(int, int, Direction, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatWall(int, int, Direction, String[])
   */
  public void print(String startString, String endString, String emptyString, String[] format) {
    validateFormat(startString, endString, emptyString, format);
    for (int y = 0; y < height; y++) {
      System.out.print("\n");
      for (int x = 0; x < width; x++) {
        System.out.print(formatCorner(x, y, format));
        System.out.print(formatWallFromCorner(x, y, Direction.RIGHT, format));
      }
      System.out.print(formatCorner(width, y, format));
      System.out.print("\n");
      for (int x = 0; x < width; x++) {
        System.out.print(formatWall(x, y, Direction.LEFT, format));
        if (x == maze.start.x && y == maze.start.y) {
          System.out.print(startString);
        } else if (x == maze.end.x && y == maze.end.y) {
          System.out.print(endString);
        } else {
          System.out.print(emptyString);
        }
      }
      System.out.print(formatWall(width-1, y, Direction.RIGHT, format));
      if (y+1 == height) {
        System.out.print("\n");
        for (int x = 0; x < width; x++) {
          System.out.print(formatCorner(x, height, format));
          System.out.print(formatWallFromCorner(x, height, Direction.RIGHT, format));
        }
        System.out.print(formatCorner(width, height, format));
      }
    }
  }

  /**
   * A method used to print the maze using the format String[].
   * Simpler version of {@link #print(String, String, String, String[])}
   * 
   * @param format  String[] containing the Strings for all possible wall combinations
   * 
   * @throws IllegalArgumentException if the format isn't valid
   * 
   * @see com.github.pirasleandro.containers.games.MazePrinter#print(String, String, String, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#validateFormat(String, String, String, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatCorner(int, int, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatWallFromCorner(int, int, Direction, String[])
   * @see com.github.pirasleandro.containers.games.MazePrinter#formatWall(int, int, Direction, String[])
   */
  public void printCompact(String format[]) {
    validateFormat(format);
    for (int y = 0; y < height; y++) {
      System.out.print("\n");
      for (int x = 0; x < width; x++) {
        System.out.print(formatCorner(x, y, format));
        System.out.print(formatWallFromCorner(x, y, Direction.RIGHT, format));
      }
      System.out.print(formatCorner(width, y, format));
      if (y+1 == height) {
        System.out.print("\n");
        for (int x = 0; x < width; x++) {
          System.out.print(formatCorner(x, height, format));
          System.out.print(formatWallFromCorner(x, height, Direction.RIGHT, format));
        }
        System.out.print(formatCorner(width, height, format));
      }
    }
  }

  /**
   * A method used by other methods to validate a format String[] and start, end, emtpy String.
   * 
   * @param startString String representing the start
   * @param endString   String representing the end
   * @param emptyString String representing an empty cell (neither start nor end)
   * @param format      String[] containing the Strings for all possible wall cases
   * 
   * @see com.github.pirasleandro.containers.games.MazePrinter#validateFormat(String[])
   * 
   * @throws IllegalArgumentException if the format String[]'s length is not 16
   * @throws IllegalArgumentException if the Strings in the format String[] don't have the same length
   * @throws IllegalArgumentException if the start, end and emtpyString don't have the same length
   */
  private void validateFormat(String startString, String endString, String emptyString, String[] format) {
    if (format.length != 16) {
      throw new IllegalArgumentException("format HashMap must have a size of 16");
    }
    if (startString.length() != endString.length() || startString.length() != emptyString.length()) {
      throw new IllegalArgumentException("all formatting strings must have the same length");
    }
    for (int i = 0; i < format.length; i++) {
      if (format[i].length() != startString.length()) {
        throw new IllegalArgumentException("all formatting strings must have the same length");
      }
    }
  }

  /**
   * A method used by other methods to validate a format String[].
   * 
   * @param format  format String[]
   * 
   * @throws IllegalArgumentException if the format String[]'s length is not 16
   * @throws IllegalArgumentException if the Strings in the format String[] don't have the same length
   */
  private void validateFormat(String[] format) {
    if (format.length != 16) {
      throw new IllegalArgumentException("format String[] must have a size of 16");
    }
    for (int i = 0; i < format.length; i++) {
      if (format[i].length() != format[0].length()) {
        throw new IllegalArgumentException("all formatting strings must have the same length");
      }
    }
  }

  /**
   * A method used by the printer methods to format a corner after the format String[]
   * 
   * @param x       x coordinate of the corner
   * @param y       y coordinate of the corner
   * @param format  String[] containing the Strings for all possible wall cases
   * @return        String formattet corner
   */
  private String formatCorner(int x, int y, String[] format) {
    StringBuilder caseBuilder = new StringBuilder();
    try {caseBuilder.append((Boolean) maze.getWallFromCorner(x, y, Direction.UP) ? "1" : "0");
    } catch (IndexOutOfBoundsException e) {caseBuilder.append("0");}
    try {caseBuilder.append((Boolean) maze.getWallFromCorner(x, y, Direction.DOWN) ? "1" : "0");
    } catch (IndexOutOfBoundsException e) {caseBuilder.append("0");}
    try {caseBuilder.append((Boolean) maze.getWallFromCorner(x, y, Direction.LEFT) ? "1" : "0");
    } catch (IndexOutOfBoundsException e) {caseBuilder.append("0");}
    try {caseBuilder.append((Boolean) maze.getWallFromCorner(x, y, Direction.RIGHT) ? "1" : "0");
    } catch (IndexOutOfBoundsException e) {caseBuilder.append("0");}
    int caseNum = Integer.parseInt(caseBuilder.toString(), 2);
    return format[caseNum];
  }

  /**
   * A method used by the printer methods to format a wall after the format String[]
   * 
   * @param x         x coordinate of the cell
   * @param y         y coordinate of the cell
   * @param direction direction from cell to wall
   * @param format    String[] containing the Strings for all possible wall cases
   * @return          String formattet wall
   */
  private String formatWall(int x, int y, Direction direction, String[] format) {
    if (direction.isVertical()) {
      return format[Integer.parseInt((Boolean) maze.getWall(x, y, direction) ? "0011" : "0000", 2)];
    } else {
      return format[Integer.parseInt((Boolean) maze.getWall(x, y, direction) ? "1100" : "0000", 2)];
    }
  }

  /**
   * A method used by the printer methods to format a wall from a corner after the format String[]
   * 
   * @param x         x coordinate of the corner
   * @param y         y coordinate of the corner
   * @param direction direction from corner to wall
   * @param format    String[] containing the Strings for all possible wall cases
   * @return          String formattet wall
   */
  private String formatWallFromCorner(int x, int y, Direction direction, String[] format) {
    if (format.length != 16) {
      throw new IllegalArgumentException("format HashMap must have a size of 16");
    }
    if (direction.isVertical()) {
      return format[Integer.parseInt((Boolean) maze.getWallFromCorner(x, y, direction) ? "1100" : "0000", 2)];
    } else {
      return format[Integer.parseInt((Boolean) maze.getWallFromCorner(x, y, direction) ? "0011" : "0000", 2)];
    }
  }
}
