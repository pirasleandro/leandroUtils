package containers.games;

import characters.Ansi;

public class MinesWeeperCell {
  protected boolean isMine;
  protected int minesNearby;

  protected MinesWeeperCell() {
    this(false, 0);
  }

  protected MinesWeeperCell(boolean isMine) {
    this(isMine, 0);
  }

  protected MinesWeeperCell(boolean isMine, int mineNeighbours) {
    this.isMine = isMine;
    this.minesNearby = mineNeighbours;
  }

  protected MinesWeeperCell(int mineNeighbours) {
    this(false, mineNeighbours);
  }

  protected MinesWeeperCell(MinesWeeperCell cell) {
    this.isMine = cell.isMine;
    this.minesNearby = cell.minesNearby;
  }

  protected boolean isMine() {
    return isMine;
  }

  protected int getMinesNearby() {
    return minesNearby;
  }

  protected void setMine(boolean isMine) {
    this.isMine = isMine;
  }

  protected void setMinesNearby(int neighbours) {
    this.minesNearby = neighbours;
  }

  protected void setMinesNearby(MinesWeeperCell... neighbours) {
    for (MinesWeeperCell cell : neighbours) {
      if (cell.isMine) {
        minesNearby++;
      }
    }
  }

  @Override
  public String toString() {
    if (isMine) {
      return Ansi.style("*", Ansi.RED, Ansi.BOLD);
    } else {
      if (minesNearby < 10) {
        switch (minesNearby) {
          case 0: return " ";
          case 1: return Ansi.style("1", Ansi.BLUE);
          case 2: return Ansi.style("2", Ansi.GREEN);
          case 3: return Ansi.style("3", Ansi.YELLOW);
          default: return Ansi.style(String.valueOf(minesNearby), Ansi.RED);
        }
      } else {
        switch (minesNearby) {
          case 10: return Ansi.style("⒑", Ansi.RED);
          case 11: return Ansi.style("⒒", Ansi.RED);
          case 12: return Ansi.style("⒓", Ansi.RED);
          default: return null;
        }
      }
    }
  }
}