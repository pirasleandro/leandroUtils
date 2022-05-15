package com.github.pirasleandro.characters;

import java.util.HashMap;

import javax.sound.sampled.Line;

/**
 * collection of all boxdrawing characters
 * 
 * this is the naming convention I used to name all finals
 * 
 *             (up)
 *              u
 *          ┌┈┈┈┃┈┈┈┐
 *          ┊   ┃   ┊
 * (left) l ━━━━╋━━━━ r (right)
 *          ┊   ┃   ┊
 *          └┈┈┈┃┈┈┈┘
 *              d
 *            (down)
 */
public class Box {
   private Box() {
      // static class
   }

   // regex to check if the finals match the naming convention (may not work if code layout was changed)
   // / ((([uU]{1,2}[234]?)?([dD]{1,2}[234]?)?|([vV]{1,2}[234]?)?)(([lL]{1,2}[234]?)?([rR]{1,2}[234]?)?|([hH]{1,2}[234]?)?)){1} = *".{1}"[;,]{1}"/g
   
   public static final String 
      LIGHT_HORIZONTAL = "─",
      LIGHT_VERTICAL = "│",
      LIGHT_DOWN_AND_RIGHT = "┌",
      LIGHT_DOWN_AND_LEFT = "┐",
      LIGHT_UP_AND_RIGHT = "└",
      LIGHT_UP_AND_LEFT = "┘",
      LIGHT_VERTICAL_AND_RIGHT = "├",
      LIGHT_VERTICAL_AND_LEFT = "┤",
      LIGHT_DOWN_AND_HORIZONTAL = "┬",
      LIGHT_UP_AND_HORIZONTAL = "┴",
      LIGHT_VERTICAL_AND_HORIZONTAL = "┼",
      DOUBLE_HORIZONTAL = "═",
      DOUBLE_VERTICAL = "║",
      DOUBLE_DOWN_AND_RIGHT = "╔",
      DOUBLE_DOWN_AND_LEFT = "╗",
      DOUBLE_UP_AND_RIGHT = "╚",
      DOUBLE_UP_AND_LEFT = "╝",
      DOUBLE_VERTICAL_AND_RIGHT = "╠",
      DOUBLE_VERTICAL_AND_LEFT = "╣",
      DOUBLE_DOWN_AND_HORIZONTAL = "╦",
      DOUBLE_UP_AND_HORIZONTAL = "╩",
      DOUBLE_VERTICAL_AND_HORIZONTAL = "╬";
   
   public static final String

   // BASIC BOX DRAWING CHARACTERS
   // normal                bold                  double
      // lines
      h  = "─", lr   = "─", H  = "━", LR   = "━", hh   = "═", llrr     = "═",
      h2 = "╌",             H2 = "╍",
      h3 = "┄",             H3 = "┅",
      h4 = "┈",             H4 = "┉",
      v  = "│", ud   = "│", V  = "┃", UD   = "┃", vv   = "║", uudd     = "║",
      v2 = "╎",             V2 = "╏",
      v3 = "┆",             V3 = "┇",
      v4 = "┊",             V4 = "┋",
      // half lines
      u  = "╵",             U  = "╹",
      d  = "╷",             D  = "╻",
      l  = "╴",             L  = "╸",
      r  = "╶",             R  = "╺",
      // corners
      dr = "┌",             DR = "┏",             ddrr = "╔",
      dl = "┐",             DL = "┓",             ddll = "╗",
      ur = "└",             UR = "┗",             uurr = "╚",
      ul = "┘",             UL = "┛",             uull = "╝",
      // T's
      vr = "├", udr  = "├", VR = "┣", UDR  = "┣", vvrr = "╠", uuddrr   = "╠",
      vl = "┤", udl  = "┤", VL = "┫", UDL  = "┫", vvll = "╣", uuddll   = "╣",
      dh = "┬", dlr  = "┬", DH = "┳", DLR  = "┳", ddhh = "╦", ddllrr   = "╦",
      uh = "┴", ulr  = "┴", UH = "┻", ULR  = "┻", uuhh = "╩", uullrr   = "╩",
      // crosses
      vh = "┼", vlr  = "┼", VH = "╋", VLR  = "╋", vvhh = "╬", vvllrr   = "╬",
                udh  = "┼",           HUD  = "╋",             uuddhh   = "╬",
                udlr = "┼",           UDLR = "╋",             uuddllrr = "╬",

   // ADVANCED BOX DRAWING CHARACTERS
   // partially bold         partially double
      // lines
      lR   = "╼",
      Lr   = "╾",
      uD   = "╽",
      Ud   = "╿",
      // corners
      dR   = "┍",            drr  = "╒",
      Dr   = "┎",            ddr  = "╓",
      dL   = "┑",            dll  = "╕",
      Dl   = "┒",            ddl  = "╖",
      uR   = "┕",            urr  = "╘",
      Ur   = "┖",            uur  = "╙",
      uL   = "┙",            ull  = "╛",
      Ul   = "┚",            uul  = "╜",
      // T's
      vR   = "┝", udR = "┝", vrr  = "╞", udrr   = "╞",
      Udr  = "┞",
      uDr  = "┟",
      Vr   = "┠", UDr = "┠", vvr  = "╟", uuddr  = "╟",
      UdR  = "┡",
      uDR  = "┢",
      vL   = "┥", udL = "┥", vll  = "╡", udll   = "╡",
      Udl  = "┦",
      uDl  = "┧",
      Vl   = "┨", UDl = "┨", vvl  = "╢", uuddl  = "╢",
      UdL  = "┩",
      uDL  = "┪",
      dLr  = "┭",
      dlR  = "┮",
      dH   = "┯", dLR = "┯", dhh  = "╤", dllrr  = "╤",
      Dh   = "┰", Dlr = "┰", uhh  = "╧", ullrr  = "╧",
      DLr  = "┱",
      DlR  = "┲",
      uLr  = "┵",
      ulR  = "┶",
      uH   = "┷", uLR = "┷", ddh  = "╥", ddlr   = "╥",
      Uh   = "┸", Ulr = "┸", uuh  = "╨", uulr   = "╨",
      ULr  = "┹",
      UlR  = "┺",
      // crosses
      vLr  = "┽", udLr = "┽",
      vlR  = "┾", udlR = "┾",
      vH   = "┿", vLR  = "┿", vhh = "╪", vllrr  = "╪",
                  udH  = "┿",            udhh   = "╪",
                  udLR = "┿",            udllrr = "╪",
      Udh  = "╀", Udlr = "╀",
      uDh  = "╁", uDlr = "╁",
      Vh   = "╂", Vlr  = "╂", vvh = "╫", vvlr   = "╫",
                  UDh  = "╂",            uuddh  = "╫",
                  UDlr = "╂",            uuddlr = "╫",
      UdLr = "╃",
      UdlR = "╄",
      uDLr = "╅",
      uDlR = "╆",
      UdH  = "╇", UdLR = "╇",
      uDH  = "╈", uDLR = "╈",
      VLr  = "╉", UDLr = "╉",
      VlR  = "╊", UDlR = "╊";

   // OTHER BOX DRAWING CHARACTERS
   public static final String
      d_r    = "╭",
      d_l    = "╮",
      u_r    = "╰",
      u_l    = "╯",
      slash  = "╱",
      bSlash = "╲",
      x      = "╳";

   public enum LineType {
      NULL,
      LIGHT,
      HEAVY,
      DOUBLE,
      DOUBLE_DASHED,
      TRIPLE_DASHED,
      QUADRUPLE_DASHED;
   }

   private static final IllegalArgumentException doesntExist = new IllegalArgumentException("This character doesn't exist");
   public static String get(String id) {
      HashMap<String, String> chars = new HashMap<>();
      chars.put("h", h);       chars.put("lr", lr);     chars.put("H", H);       chars.put("LR", LR);
      chars.put("hh", hh);     chars.put("llrr", llrr); chars.put("h2", h2);     chars.put("H2", H2);
      chars.put("h3", h3);     chars.put("H3", H3);     chars.put("h4", h4);     chars.put("H4", H4);
      chars.put("v", v);       chars.put("ud", ud);     chars.put("V", V);       chars.put("UD", UD);
      chars.put("vv", vv);     chars.put("uudd", uudd); chars.put("v2", v2);     chars.put("V2", V2);
      chars.put("v3", v3);     chars.put("V3", V3);     chars.put("v4", v4);     chars.put("V4", V4);
      chars.put("u", u);       chars.put("U", U);       chars.put("d", d);       chars.put("D", D);
      chars.put("l", l);       chars.put("L", L);       chars.put("r", r);       chars.put("R", R);
      chars.put("dr", dr);     chars.put("dl", dl);     chars.put("ur", ur);     chars.put("ul", ul);
      chars.put("DR", DR);     chars.put("DL", DL);     chars.put("UR", UR);     chars.put("UL", UL);
      chars.put("ddrr", ddrr); chars.put("ddll", ddll); chars.put("uurr", uurr); chars.put("uull", uull);
      chars.put("vr", vr);     chars.put("vl", vl);     chars.put("uh", uh);     chars.put("dh", dh);
      chars.put("udr", udr);   chars.put("udl", udl);   chars.put("ulr", ulr);   chars.put("dlr", dlr);
      chars.put("VR", VR);     chars.put("VL", VL);     chars.put("UH", UH);     chars.put("DH", DH);
      // TODO
      
      return chars.get(id);
   }
}