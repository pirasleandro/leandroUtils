package com.github.pirasleandro.characters;

public class Block {
   private Block() {
      // static class
   }

   /*

   blocks
      u  = upper
      lo = lower
      r  = right
      le = left
      f  = full

      - blocks are "attached" to the give side
      - the size of the block is declared by a division

   divison
      x_y = x out of y
      h   = half
      
   quadrants:
      0 = empty
      1 = filled

      - quadrant symbols start with "q" followed by four binary digits
      - every digit represents a quadrant
      - they are mentioned in this order:
         - top left
         - top right
         - bottom left
         - bottom right

   other symbols:
      light =  "░";
      medium = "▒";
      dark =   "▓";


   */
   public static final String FULL_BLOCK = "█";

   public static final String

   // BASIC BLOCK ELEMENTS
   // upper                       lower                         left                         right
      UP_1_8 = "▔",              LO_1_8 =  "▁",                LE_1_8 = "▏",               RI_1_8 = "▕",
                                  LO_2_8 = "▂", LO_1_4 = "▂", LE_2_8 = "▎", LE_1_4 = "▎",
                                  LO_3_8 = "▃",                LE_3_8 = "▍",
      UP_4_8 = "▀", UP_2_4 = "▀", LO_4_8 =  "▄", LO_2_4 =  "▄", LE_4_8  = "▌", LE_2_4 = "▌", RI_4_8 = "▐", RI_2_4 = "▐",
                    UP_1_2 = "▀",                LO_1_2 =  "▄",                LE_1_2 = "▌",               RI_1_2 = "▐",
                                  LO_5_8 = "▅",                LE_5_8 = "▋",
                                  LO_6_8 = "▆", LO_3_4 = "▆", LE_6_8 = "▊", LE_3_4 = "▊",
                                  LO_7_8 = "▇",                LE_7_8 = "▉",
      UP_8_8 = "█", UP_4_4 = "█", LO_8_8 =  "█", LO_4_4  = "█", LE_8_8 = "█", LE_4_4 = "█", RI_8_8 = "█", RI_4_4 = "█",
                    UP_2_2 = "█",                LO_2_2  = "█",               LE_2_2 = "█",               RI_2_2 = "█",
                    UP_1_1 = "█",                LO_1_1  = "█",               LE_1_1 = "█",               RI_1_1 = "█",

   // SHADES
      LIGHT      = "░",
      MEDIUM     = "▒",
      DARK       = "▓",
      
   // QUADRANTS
   //               recreation out of
   // actual        full and half blocks
                    Q0000 = "  ",
      q0001 = "▗", Q0001 = " ▄",
      q0010 = "▖", Q0010 = "▄ ",
                    Q0011 = "▄▄",
      q0100 = "▝", Q0100 = " ▀",
                    Q0101 = " █",
      q0110 = "▞", Q0110 = "▄▀",
      q0111 = "▟", Q0111 = "▄█",
      q1000 = "▘", Q1000 = "▀ ",
      q1001 = "▚", Q1001 = "▀▄",
      q1011 = "▙", Q1011 = "█▄",
      q1101 = "▜", Q1101 = "▀█",
      q1110 = "▛", Q1110 = "█▀",
                    Q1111 = "██";

   public static String getQsOf(String binary) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < 2; i++) {
         if (binary.charAt(i) == binary.charAt(i+2)) {
            sb.append(binary.charAt(i) == '1' ? "█" : " ");
         } else {
            sb.append(binary.charAt(i) == '1' ? "▀" : "▄");
         }
      }
      return sb.toString();
   }
}
