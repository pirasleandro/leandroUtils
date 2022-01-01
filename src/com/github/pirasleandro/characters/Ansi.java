package com.github.pirasleandro.characters;

public class Ansi {

  private Ansi() {
    // static class
  }
  //#region FINAL INTS FOR PARAMETERS

  /** 
   * BRI = bright
   * BG  = background
  */
  public static final int BLACK = 30;
  public static final int RED = 31;
  public static final int GREEN = 32;
  public static final int YELLOW = 33;
  public static final int BLUE = 34;
  public static final int PURPLE = 35;
  public static final int CYAN = 36;
  public static final int WHITE = 37;

  private static final int ADD_FOR_BG = 10;
  private static final int ADD_FOR_BRIGHT = 60;

  // MOST CODES ONLY WORK UNDER SPECIFIC CONDITIONS OR MAY NEED FURTHER ARGUMENTS TO WORK
  // -> https://en.wikipedia.org/wiki/ANSI_escape_code#SGR_(Select_Graphic_Rendition)_parameters
  public static final int RESET = 0;
  public static final int BOLD = 1;
  public static final int FAINT = 2;
  public static final int ITALIC = 3;
  public static final int UNDERLINE = 4;
  public static final int SLOW_BLINK = 5;
  public static final int RAPID_BLINK = 6;
  public static final int INVERT = 7;
  public static final int CONCEAL = 8;
  public static final int CROSSED_OUT = 9;
  public static final int DEFAULT_FONT = 10;
  public static final int ALTERNATIVE_FONT = 11;
  public static final int GOTHIC = 20;
  public static final int DOUBLE_UNDERLINED_OR_NOT_BOLD = 21;
  public static final int NORMAL_INTENSITY = 22;
  public static final int NEITHER_ITALIC_NOR_BLACKLETTERS = 23;
  public static final int NOT_UNDERLINED = 24;
  public static final int NOT_BLINKING = 25;
  public static final int PROPORTIONAL_SPACING = 26;
  public static final int NOT_REVERSED = 27;
  public static final int REVEAL = 28;
  public static final int NOT_CROSSED_OUT = 29;
  public static final int SET_COLOR = 38;
  public static final int SET_BG_COLOR = 48;
  public static final int SET_DEFAULT_BG_COLOR = 49;
  public static final int DISABLE_PROPORTIONAL_SPACING = 50;
  public static final int SET_UNDERLINE_COLOR = 58;
  public static final int DEFAULT_UNDERLINE_COLOR = 59;
  public static final int SUPERSCRIPT = 73;
  public static final int SUBSCRIPT = 74;
  public static final int NEITHER_SUPER_NOR_SUBSCRIPT = 75;
  //#endregion

  private static IllegalArgumentException IllegalColorCode = new IllegalArgumentException(
    "argument must be a foreground or background color code");

  public static int background(int color) {
    if ((color < 30 || color > 37) && (color < 90 || color > 97)) throw IllegalColorCode;
    return color += ADD_FOR_BG;
  }
  public static int brighten(int color) {
    if ((color < 30 || color > 37) && (color < 40 || color > 47)) throw IllegalColorCode;
    return color += ADD_FOR_BRIGHT;
  }

  public static String style(String input, int... parameters) {
    return (generateCode(parameters) + input + generateCode(RESET));
  }

  public static String generateCode(int... parameters) {
    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append("\u001B[");
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i] < 0 || parameters[i] > 107) throw new IllegalArgumentException("parameters must be in the range from 0 to 107");
      outputBuilder.append(parameters[i]);
      if (i+1 != parameters.length) {
        outputBuilder.append(";");
      } else {
        outputBuilder.append("m");
      }
    }
    return outputBuilder.toString();
  }

  public static String bold(String input) {
    return style(input, Ansi.BOLD);
  }

  public static String underline(String input) {
    return style(input, Ansi.UNDERLINE);
  }

  public static void cls() {
    System.out.print("\033[H\033[2J");  
    System.out.flush(); 
  }
}
