package com.github.pirasleandro.containers.games;

import java.util.ArrayList;

import com.github.pirasleandro.characters.Ansi;
import com.github.pirasleandro.characters.Block;
import com.github.pirasleandro.characters.Box;
import com.github.pirasleandro.containers.Slot;
import com.github.pirasleandro.containers.TriGrid;
import com.github.pirasleandro.containers.TriPoint;
import com.github.pirasleandro.containers.games.TriMinesweeper.*;

public class TriMinesWeeperPrinter {
  private TriMinesweeper game;
  private static final String slash =             Box.slash;
  private static final String boldSlash =         Ansi.bold("/");
  private static final String selectedSlash =     slash;
  private static final String backSlash =         Box.bSlash;
  private static final String boldBackSlash =     Ansi.bold("\\");
  private static final String selectedBackSlash = backSlash;
  private static final String dash =              Box.h.repeat(3);
  private static final String boldDash =          Box.R + Box.H + Box.L;
  private static final String selectedDash =      Box.r + Box.h + Box.l;
  private static final String cross =             Box.x;
  private static final String boldCross =         Ansi.bold("X");
  private boolean complexCross = true;
  private static final String boldCrossA =        "▲";
  private static final String boldCrossB =        "▼";
  @SuppressWarnings("unused")
  private static final String selectedCrossA =    Ansi.style(cross, Ansi.brighten(Ansi.BLACK));
  @SuppressWarnings("unused")
  private static final String selectedCrossB =    Ansi.style(cross, Ansi.brighten(Ansi.BLACK));
  private static final String topCross =          Box.h;//"‾"
  private static final String bottomCross =       Box.h;//"_"
  private static final String topLeftCross =      "⸝";//"⦧"
  private static final String topRightCross =     "⸜";//Box.l_d
  private static final String leftCross =         "⟨";
  private static final String rightCross =        "⟩";
  private static final String slashCross =        "/";
  private static final String backSlashCross =    "\\";
  private static final String bottomLeftCross =   "⸌";//"⦦"
  private static final String bottomRightCross =  "⸍";//Box.l_u
  private static final String hiddenA = Block.LIGHT;//▲
  private static final String hiddenB = Block.LIGHT;//▼
  private static final String boldHiddenA = Ansi.bold(Block.MEDIUM);
  private static final String boldHiddenB = Ansi.bold(Block.MEDIUM);
  private static final String flag = "⚑";
  private static final String boldFlag = Ansi.style(flag, Ansi.BOLD, Ansi.UNDERLINE);
  private static final String mark = "⚐";
  private static final String boldMark = Ansi.style(mark, Ansi.BOLD, Ansi.UNDERLINE);

  @SuppressWarnings("unused")
  private TriMinesWeeperPrinter() {
  }

  protected TriMinesWeeperPrinter(TriMinesweeper game) {
    this.game = game;
  }

  protected void printGame() {
    Ansi.cls();
    printGameInfo();
    for (int y = 0; y < game.size; y++) {
      //   ╳┈   ←
      //  ╱x╲
      // ╳───╳┈
      System.out.print("\n" + "  ".repeat((game.size-y)-1) + "  ");
      for (int x = 0; x < y+1; x++) {
        //  ╳───
        //  ↑
        System.out.print(formatCross(x, y));
        //  ╳───
        //    ↑
        if (TriGrid.hasTwoSlots(x, y)) {
          System.out.print(formatDash(x, y));
        }
      }
      //   ╳┈
      //  ╱x╲   ←
      // ╳───╳┈
      System.out.print("\n" + "  ".repeat((game.size-y)-1) + " ");
      for (int x = 0; x < y+1; x++) {
        //  ╱x╲x
        //  ↑
        System.out.print(formatSlash(x, y));
        //  ╱x╲x
        //   ↑
        System.out.print(formatCell(x, y, Slot.A));
        //  ╱x╲x
        //    ↑
        System.out.print(formatBackSlash(x, y));
        //  ╱x╲x
        //     ↑
        if (TriGrid.hasTwoSlots(x, y)) {
          System.out.print(formatCell(x, y, Slot.B));
        }
      }
    }
    //   ╳┈
    //  ╱x╲
    // ╳───╳┈ ←
    System.out.print("\n");
    for (int x = 0; x < game.size+1; x++) {
      //  ╳───
      //  ↑
      System.out.print(formatCross(x, game.size));
      //  ╳───
      //    ↑
      if (TriGrid.hasTwoSlots(x, game.size)) {
        System.out.print(formatDash(x, game.size));
      }
    }
  }

  /**
   *     LEFT    RIGHT
   *     ?───?───?   
   *    ╱ ╲ ╱ ╲ ╱ ╲   TOP
   *   ?───╳───?───?
   *  ╱ ╲ ╱A╲B╱ ╲ ╱   CENTER
   * ?───?───╳───?
   *  ╲ ╱ ╲ ╱ ╲ ╱     BOTTOM
   *   ?───?───?
   *  CENTER
   */

  private String formatCross(int x, int y) {
    StringBuilder caseBuilder = new StringBuilder();
    caseBuilder.append(neighboursContain(x-1, y-1, Slot.A));
    caseBuilder.append(neighboursContain(x-1, y-1, Slot.B));
    caseBuilder.append(neighboursContain(x, y-1, Slot.A));
    caseBuilder.append(",");
    caseBuilder.append(neighboursContain(x-1, y, Slot.B));
    caseBuilder.append(neighboursContain(x, y, Slot.A));
    caseBuilder.append(neighboursContain(x, y, Slot.B));
    String caseString = caseBuilder.toString().replace("true", "1").replace("false", "0");

    if (
      (game.cursor.equals(x, y, Slot.A) ||
      game.cursor.equals(x, y, Slot.B) ||
      game.cursor.equals(x-1, y-1, Slot.A) ||
      game.cursor.equals(x-1, y-1, Slot.B) ||
      game.cursor.equals(x, y-1, Slot.A) ||
      game.cursor.equals(x-1, y, Slot.B)) &&
      !complexCross
      ) {
        return boldCross;
    } else if (
      game.cursor.equals(x, y, Slot.A) || 
      game.cursor.equals(x-1, y-1, Slot.A) || 
      game.cursor.equals(x, y-1, Slot.A)) {
        return boldCrossA;
    } else if (
      game.cursor.equals(x, y, Slot.B) || 
      game.cursor.equals(x-1, y-1, Slot.B) || 
      game.cursor.equals(x-1, y, Slot.B)) {
        return boldCrossB;
    } else {
      switch (caseString) {
        case "001,011": //fallthrough
        case "110,100": return slashCross;
        case "100,110": //fallthrough
        case "011,001": return backSlashCross;
        case "000,011": return topLeftCross;
        case "111,000": return bottomCross;
        case "000,110": return topRightCross;
        case "001,001": return leftCross;
        case "100,100": return rightCross;
        case "011,000": return bottomLeftCross;
        case "000,111": return topCross;
        case "110,000": return bottomRightCross;
        default:        return cross;
      }
    }
  }

  private String formatDash(int x, int y) {
    TriPoint slotB = new TriPoint(x, y, Slot.B);
    TriPoint slotA = new TriPoint(x, y-1, Slot.A);
    String output;
    if (
      game.cursor.equals(slotB) || 
      game.cursor.equals(slotA)) {
        output = boldDash;
    } else if (
      neighboursContain(slotB) ||
      neighboursContain(slotA)) {
        output = selectedDash;
    } else {
      output = dash;
    }
    if (
      (game.board.areCordsValid(slotA) && game.tooManyFlags(slotA) && game.isCoverState(slotA, Cover.UNCOVERED)) || 
      (game.board.areCordsValid(slotB) && game.tooManyFlags(slotB) && game.isCoverState(slotB, Cover.UNCOVERED))) {
        return Ansi.style(output, Ansi.RED);
    } else {
      return output;
    }
  }

  private String formatCell(int x, int y, Slot slot) {
    TriPoint current = new TriPoint(x, y, slot);
    switch((Cover) game.cover.get(current)) {
      case COVERED: {
        if (game.cursor.equals(current) || neighboursContain(current)) {
          return (slot.isA() ? boldHiddenA : boldHiddenB);
        } else {
          return (slot.isB() ? hiddenA : hiddenB);
        }
      }
      case UNCOVERED: {
        if (
          (game.cursor.equals(current) || neighboursContain(current)) && 
          game.board.get(current).toString() != " ") {
            return Ansi.style(game.board.get(current).toString(), Ansi.BOLD, Ansi.UNDERLINE);
        } else {
          return game.board.get(current).toString();
        }
      }
      case FLAGGED: {
        if (game.cursor.equals(current) || neighboursContain(current)) {
          if (neighboursHasTooManyFlags(current)) return Ansi.style(boldFlag, Ansi.RED);
            else return boldFlag;
        } else {
          if (neighboursHasTooManyFlags(current)) return Ansi.style(flag, Ansi.RED);
            else return flag;
        }
      }
      case MARKED: {
        if (game.cursor.equals(current) || neighboursContain(current)) {
          if (neighboursHasTooManyFlags(current)) return Ansi.style(boldMark, Ansi.RED);
            else return boldMark;
        } else {
          if (neighboursHasTooManyFlags(current)) return Ansi.style(mark, Ansi.RED);
            else return mark;
        }
      }
      default: throw new EnumConstantNotPresentException(Cover.class, String.valueOf((Cover) game.cover.get(x, y, slot)));
    }
  }

  private String formatSlash(int x, int y) {
    TriPoint slotA = new TriPoint(x, y, Slot.A);
    TriPoint slotB = new TriPoint(x-1, y, Slot.B);
    String output;
    if (game.cursor.equals(slotA) || game.cursor.equals(slotB)) {
      output = boldSlash;
    } else if (neighboursContain(slotA) || neighboursContain(slotB)) {
      output = selectedSlash;
    } else {
      output = slash;
    }
    if (
      (game.board.areCordsValid(slotA) && game.tooManyFlags(slotA) && game.isCoverState(slotA, Cover.UNCOVERED)) || 
      (game.board.areCordsValid(slotB) && game.tooManyFlags(slotB) && game.isCoverState(slotB, Cover.UNCOVERED))) {
        return Ansi.style(output, Ansi.RED);
    } else {
      return output;
    }
  }

  private String formatBackSlash(int x, int y) {
    TriPoint slotA = new TriPoint(x, y, Slot.A);
    TriPoint slotB = new TriPoint(x, y, Slot.B);
    String output;
    if (game.cursor.equals(slotA) || game.cursor.equals(slotB)) {
      output = boldBackSlash;
    } else if (neighboursContain(slotA) || neighboursContain(slotB)) {
      output = selectedBackSlash;
    } else {
      output = backSlash;
    }
    if (
      (game.board.areCordsValid(slotA) && game.tooManyFlags(slotA) && game.isCoverState(slotA, Cover.UNCOVERED)) || 
      (game.board.areCordsValid(slotB) && game.tooManyFlags(slotB) && game.isCoverState(slotB, Cover.UNCOVERED))) {
        return Ansi.style(output, Ansi.RED);
    } else {
      return output;
    }
  }

  private void printGameInfo() {
    System.out.println(
      "[" + game.cursor.x + "/" + game.cursor.y + "/" + game.cursor.slot + "] " + 
      "mines left:" + (game.totalMines - game.mistakes - game.flags) + "\n");
    System.out.println(formatMistakes());
  }

  private static final String fiveMistakes = Box.vh.repeat(4);
  private static final String oneMistake = Box.v;
  private String formatMistakes() {
    int mistakes = game.mistakes;
    int fives = 0;
    int rest = 0;
    while (mistakes > 0) {
      if (mistakes%5 > 0) {
        rest++;
        mistakes--;
      } else {
        fives = mistakes/5;
        mistakes = 0;
      }
    }
    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append((fiveMistakes + " ").repeat(fives));
    outputBuilder.append(oneMistake.repeat(rest));
    if (game.mistakes > 0) {
      outputBuilder.append(" mistakes were made");
    } else {
      outputBuilder.append("no mistakes");
    }
    return outputBuilder.toString();
  }

  private static final String emptyStar = "☆";
  private static final String star = "★";
  public void printResults() {
    int emptyStarCount = (game.mistakes < 5 ? game.mistakes : 5);
    String stars = star.repeat(5-emptyStarCount) + emptyStar.repeat(emptyStarCount);
    System.out.println(stars + "\n" + formatMistakes());
  }

  private boolean neighboursHasTooManyFlags(TriPoint point) {
    ArrayList<TriPoint> neighbours = game.neighbourCordsOf(point);
    for (TriPoint neighbour : neighbours) {
      if (game.tooManyFlags(neighbour)) {
        return true;
      }
    }
    return false;
  }

  private boolean neighboursContain(int x, int y, Slot slot) {
    return neighboursContain(new TriPoint(x, y, slot));
  }

  private boolean neighboursContain(TriPoint point) {
    return game.neighbourCordsOf(game.cursor).contains(point);
  }
}
