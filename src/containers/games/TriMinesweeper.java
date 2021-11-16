package containers.games;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import containers.Direction;
import containers.Slot;
import containers.TriGrid;
import containers.TriPoint;

public class TriMinesweeper {
  protected int size;
  protected TriGrid<MinesWeeperCell,Boolean> board;
  protected enum Cover {
    COVERED,
    UNCOVERED,
    FLAGGED,
    MARKED;
  }
  protected TriGrid<Cover,Boolean> cover;
  protected int totalMines;
  protected int flags;
  protected int mistakes;
  private boolean isGenerated = false;
  private Random random = new Random();
  private Scanner scan = new Scanner(System.in);
  protected TriPoint cursor;
  private TriMinesWeeperPrinter printer;

  public TriMinesweeper() {
    this(10);
  }

  public TriMinesweeper(int size) {
    if (size < 5) {
      throw new IllegalArgumentException("size must not be smaller than 3");
    }
    this.size = size;
    totalMines = ((size*size)/100)*15;
    flags = 0;
    mistakes = 0;
    board = new TriGrid<>(size, new MinesWeeperCell(), null);
    cover = new TriGrid<>(size, Cover.COVERED, null);
    cursor = new TriPoint(TriGrid.getCenterCords(size));
    printer = new TriMinesWeeperPrinter(this);
  }

  public void printDebug() {
    printer.printGame();
  }

  public void start() {
    boolean gameOver = false;
    while (!gameOver) {
      printer.printGame();
      switch (scan.nextLine()) {
        case "w" -> move(Direction.UP);
        case "a" -> move(Direction.LEFT);
        case "s" -> move(Direction.DOWN);
        case "d" -> move(Direction.RIGHT);
        case "q" -> toggleSlot();
        case "f" -> toggleFlag();
        case "c" -> toggleMark();
        case "e" -> uncover();
      }
      gameOver = gameIsOver();
    }
    printer.printResults();
  }

  private void generate(TriPoint cursor) {
    ArrayList<TriPoint> options = new ArrayList<>();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < y+1; x++) {
        options.add(new TriPoint(x, y, Slot.A));
        if (TriGrid.hasTwoSlots(x, y)) {
          options.add(new TriPoint(x, y, Slot.B));
        }
      }
    }
    options.remove(cursor);
    options.removeAll(neighbourCordsOf(cursor));
    for (int i = 0; i < totalMines; i++) {
      TriPoint randomPoint = new TriPoint(options.get(random.nextInt(options.size())));
      MinesWeeperCell temp = new MinesWeeperCell((MinesWeeperCell) board.get(randomPoint));
      temp.setMine(true);
      board.set(randomPoint, temp);
      informNeighbours(randomPoint);
      options.remove(randomPoint);
    }
    isGenerated = true;
  }

  private void informNeighbours(TriPoint point) {
    for (TriPoint triPoint : neighbourCordsOf(point)) {
      increaseMinesNearby(triPoint);
    }
  }

  private void increaseMinesNearby (TriPoint point) {
    MinesWeeperCell temp = new MinesWeeperCell();
    if (board.areCordsValid(point)) {
      if (board.get(point) instanceof MinesWeeperCell) {
        temp = new MinesWeeperCell((MinesWeeperCell) board.get(point));
      } else {
        System.out.println("NOT CELL[x:" + point.x + " y:" + point.y + " slot:" + point.slot);
      }
      temp.minesNearby++;
      board.set(point, temp);
    }
  }

  private void move(Direction direction) {
    switch (direction) {
      case UP: {
        if (cursor.slot.isA()) {
          if (board.areCordsValid(cursor.x-1, cursor.y-1, cursor.slot.other())) {
            cursor.translate(-1, -1, cursor.slot.other());
          }
        } else {
          if (board.areCordsValid(cursor.x, cursor.y-1, cursor.slot.other())) {
            cursor.translate(0, -1, cursor.slot.other());
          }
        }
        break;
      }
      case DOWN: {
        if (cursor.slot.isA()) {
          if (board.areCordsValid(cursor.x, cursor.y+1, cursor.slot.other())) {
            cursor.translate(0, 1, cursor.slot.other());
          }
        } else {
          if (board.areCordsValid(cursor.x+1, cursor.y+1, cursor.slot.other())) {
            cursor.translate(1, 1, cursor.slot.other());
          }
        }
        break;
      }
      case LEFT: {
        if (board.areCordsValid(cursor.x-1, cursor.y, cursor.slot)) {
          cursor.translate(-1, 0);
        }
        break;
      }
      case RIGHT: {
        if (board.areCordsValid(cursor.x+1, cursor.y, cursor.slot)) {
          cursor.translate(1, 0);
        }
        break;
      }
      default: return;
    }
  }

  private void toggleSlot() {
    if (board.areCordsValid(cursor.x, cursor.y, cursor.slot.other())) {
      cursor.slot = cursor.slot.other();
    }
  }

  private void toggleFlag(TriPoint current) {
    switch((Cover) cover.get(current)) {
      case COVERED: //fallthrough
      case MARKED: {
        cover.set(current, Cover.FLAGGED);
        flags++; break;
      }
      case FLAGGED: {
        cover.set(current, Cover.COVERED);
        flags--; break;
      }
      case UNCOVERED: {
        ArrayList<TriPoint> neighbours = neighbourCordsOf(current);
        boolean allFlagged = (
          neighboursAtCoverstate(current, Cover.FLAGGED) > 0 && 
          neighboursAtCoverstate(current, Cover.COVERED) == 0 &&
          neighboursAtCoverstate(current, Cover.MARKED) == 0);
        for (TriPoint neighbour : neighbours) {
          switch ((Cover) cover.get(neighbour)) {
            case COVERED: {
              cover.set(neighbour, Cover.FLAGGED);
              flags++; break;
            }
            case MARKED: {
              cover.set(neighbour, Cover.FLAGGED);
              flags++; break;
            }
            case FLAGGED: {
              if (allFlagged) {
                cover.set(neighbour, Cover.COVERED);
                flags--; break;
              }
            }
            default: break;
          }
        }
        break;
      }
    }
  }

  private void toggleFlag() {
    toggleFlag(cursor);
  }

  private void toggleMark(TriPoint current) {
    switch ((Cover) cover.get(current)) {
      case COVERED    -> cover.set(current, Cover.MARKED);
      case UNCOVERED  -> {
        ArrayList<TriPoint> neighbours = neighbourCordsOf(current);
        boolean allMarked = (
          neighboursAtCoverstate(current, Cover.MARKED) > 0 && 
          neighboursAtCoverstate(current, Cover.COVERED) == 0);
        for (TriPoint neighbour : neighbours) {
          switch ((Cover) cover.get(neighbour)) {
            case COVERED: cover.set(neighbour, Cover.MARKED);   break;
            case MARKED: {
              if (allMarked) {
                cover.set(neighbour, Cover.COVERED);  break;
              } else {
                cover.set(neighbour, Cover.MARKED);  break;
              }
            }
            default: break;
          }
        }
      }
      case FLAGGED    -> {
        cover.set(current, Cover.MARKED);
        flags--;
      }
      case MARKED     -> cover.set(current, Cover.COVERED);
    }
  }

  private void toggleMark() {
    toggleMark(cursor);
  }

  /**
   * A method used to allow the player to uncover a cell if they believe it not to be a mine.
   * Changes the {@code Cover} enum of the MinesweeperCell at the specified TriPoint to {@code Cover.UNCOVERED}
   * if it is neither flagged nor marked. Triggers the recursive function {@link #uncoverChain(TriPoint)}
   * if the cells nearby mines is 0.
   * 
   * @see containers.games.TriMinesweeper#uncoverChain(TriPoint)
   */
  private void uncover(TriPoint current) {
    if (!isGenerated) {
      generate(current);
    }
    if (cover.get(current).equals(Cover.COVERED)) {
      uncoverChain(current);
    } else if (
      cover.get(current).equals(Cover.UNCOVERED) && (
        neighboursAtCoverstate(current, Cover.FLAGGED) + uncoveredMinesAround(current) == minesNearbyOf(current) || 
        neighboursAtCoverstate(current, Cover.MARKED) > 0) &&
      neighboursAtCoverstate(current, Cover.COVERED) > 0) {
        ArrayList<TriPoint> neighbours = neighbourCordsOf(current);
        for (TriPoint neighbour : neighbours) {
          uncoverChain(neighbour);
        }
    }
  }

  private void uncover() {
    uncover(cursor);
  }

  /**
   * A recursive method used to uncover clusters of cells with no mines nearby.
   * Triggered by {@link #uncover()}.
   * 
   * @param current TriPoint of the current cell
   * 
   * @see containers.games.TriMinesweeper#uncover()
   */
  private void uncoverChain(TriPoint current) {
    MinesWeeperCell currentCell = new MinesWeeperCell();
    if (board.get(current) instanceof MinesWeeperCell) {
      currentCell = (MinesWeeperCell) board.get(current);
    } else {
      throw new IndexOutOfBoundsException(current.toString() + " is not a cell");
    }
    if (cover.get(current).equals(Cover.COVERED)) {
      cover.set(current, Cover.UNCOVERED);
      if (isMine(current)) {
        mistakes++;
      }
      if (currentCell.getMinesNearby() == 0 && !currentCell.isMine) {
        for (TriPoint next : neighbourCordsOf(current)) {
          uncoverChain(next);
        }
      }
    }
  }

  private boolean gameIsOver() {
    return (totalMines - flags == 0 && coveredCells() == 0);
  }

  protected boolean tooManyFlags(TriPoint point) {
    if (isMine(point) || !cover.get(point).equals(Cover.UNCOVERED)) {
      return false;
    } else if (neighboursAtCoverstate(point , Cover.FLAGGED) > minesNearbyOf(point)) {
      return true;
    } else if (
      neighboursAtCoverstate(point , Cover.FLAGGED) == minesNearbyOf(point) && 
      neighboursAtCoverstate(point , Cover.MARKED) > 0) {
        return true;
    } else {
      return false;
    }
  }

  protected boolean tooManyFlags(int x, int y, Slot slot) {
    return tooManyFlags(new TriPoint(x, y, slot));
  }

  protected int minesNearbyOf(TriPoint point) {
    if (board.get(point) instanceof MinesWeeperCell) {
      MinesWeeperCell cell = (MinesWeeperCell) board.get(point);
      return cell.minesNearby;
    } else {
      throw new IllegalArgumentException(
        "The given " + point.toString() + " in the TriGrid is not a MinesWeeperCell: " 
        + board.get(point).getClass());
    }
  }

  private int coveredCells() {
    int count = 0;
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < y+1; x++) {
        Cover current = (Cover) cover.get(x, y, Slot.A);
        if (current.equals(Cover.COVERED)) {
          count++;
        }
      }
    }
    return count;
  }

  private int uncoveredMinesAround(TriPoint point) {
    int output = 0;
    for (TriPoint neighbour : neighbourCordsOf(point)) {
      if (((MinesWeeperCell) board.get(neighbour)).isMine && cover.get(neighbour).equals(Cover.UNCOVERED)) {
        output++;
      }
    }
    return output;
  }

  /**
   * A method used to count the neighbours of the given point with the specified coverstate.
   * 
   * @param point       TriPoint whos neighbours are to be counted
   * @param coverstate  Cover that is tested for
   * @return            int count of neighbours at the specified coverstate
   */
  protected int neighboursAtCoverstate(TriPoint point, Cover coverstate) {
    ArrayList<TriPoint> neighbours = neighbourCordsOf(point);
    int output = 0;
    for (TriPoint neighbour : neighbours) {
      if (cover.get(neighbour).equals(coverstate)) {
        output++;
      }
    }
    return output;
  }

  protected boolean isCoverState(TriPoint point, Cover state) {
    return cover.get(point).equals(state);
  }

  private boolean isMine(TriPoint point) {
    if (board.get(point) instanceof MinesWeeperCell) {
      MinesWeeperCell cell = (MinesWeeperCell) board.get(point);
      return cell.isMine;
    } else {
      throw new IllegalArgumentException(
        "The given " + point.toString() + " in the TriGrid is not a MinesWeeperCell: " 
        + board.get(point).getClass());
    }
  }

  protected ArrayList<TriPoint> neighbourCordsOf(TriPoint point) {
    return neighbourCordsOf(point, board);
  }

  protected static ArrayList<TriPoint> neighbourCordsOf(TriPoint point, TriGrid<MinesWeeperCell,Boolean> board) {
    int x = point.x;
    int y = point.y;
    Slot slot = point.slot;
    ArrayList<TriPoint> output = new ArrayList<>();
    if (board.areCordsValid(x-1, y-1, Slot.A))  output.add(new TriPoint(x-1, y-1, Slot.A));
    if (board.areCordsValid(x-1, y-1, Slot.B))  output.add(new TriPoint(x-1, y-1, Slot.B));
    if (board.areCordsValid(x, y-1, Slot.A))    output.add(new TriPoint(x, y-1, Slot.A));
    if (slot.isB()) {
      if (board.areCordsValid(x, y-1, Slot.B))    output.add(new TriPoint(x, y-1, Slot.B));
      if (board.areCordsValid(x+1, y-1, Slot.A))  output.add(new TriPoint(x+1, y-1, Slot.A));
    } else {
      if (board.areCordsValid(x-1, y, Slot.A))  output.add(new TriPoint(x-1, y, Slot.A));
    }
    if (board.areCordsValid(x-1, y, Slot.B))      output.add(new TriPoint(x-1, y, Slot.B));
    if (board.areCordsValid(x, y, slot.other()))  output.add(new TriPoint(x, y, slot.other()));
    if (board.areCordsValid(x+1, y, Slot.A))      output.add(new TriPoint(x+1, y, Slot.A));
    if (slot.isB()) {
      if (board.areCordsValid(x+1, y, Slot.B))  output.add(new TriPoint(x+1, y, Slot.B));
    } else {
      if (board.areCordsValid(x-1, y+1, Slot.B))  output.add(new TriPoint(x-1, y+1, Slot.B));
      if (board.areCordsValid(x, y+1, Slot.A))    output.add(new TriPoint(x, y+1, Slot.A));
    }
    if (board.areCordsValid(x, y+1, Slot.B))    output.add(new TriPoint(x, y+1, Slot.B));
    if (board.areCordsValid(x+1, y+1, Slot.A))  output.add(new TriPoint(x+1, y+1, Slot.A));
    if (board.areCordsValid(x+1, y+1, Slot.B))  output.add(new TriPoint(x+1, y+1, Slot.B));
    return output;
  }
}
