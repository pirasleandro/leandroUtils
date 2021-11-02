package characters;

public class Box {
   private Box() {
      // static class
   }

   /*

   directions:
      v = vertical
      h = horizontal

      u = up
      l = left
      r = right
      d = down

      - the directions are mentioned in this order:
         - vertical, up, horizontal, left, right, down
      - the type of line is declared by conjugations/suffixes
      - is a direction empty, it is skipped

   conjugations/suffixes (x replaces directionkey):
      X   = heavy (uppercase)
      x3  = triple dashed (-3)
      x4  = quadruple dashed (-4)
      xx  = double (double)
      x_y = rounded corner (two directions connected by underline)

   other symbols:
      slash       = ╱
      bSlash   = ╲
      X           = ╳

   */

   public static final String h = "─";
   public static final String H = "━";
   public static final String v = "│";
   public static final String V = "┃";
      public static final String lr = "─";
      public static final String LR = "━";
      public static final String ud = "│";
      public static final String UD = "┃";

   public static final String h3 = "┄";
   public static final String H3 = "┅";
   public static final String v3 = "┆";
   public static final String V3 = "┇";
      public static final String l3r3 = "┄";
      public static final String L3R3 = "┅";
      public static final String u3d3 = "┆";
      public static final String U3D3 = "┇";

   public static final String h4 = "┈";
   public static final String H4 = "┉";
   public static final String v4 = "┊";
   public static final String V4 = "┋";
      public static final String l4r4 = "┈";
      public static final String L4R4 = "┉";
      public static final String u4d4 = "┊";
      public static final String U4D4 = "┋";

   public static final String rd = "┌";
   public static final String Rd = "┍";
   public static final String rD = "┎";
   public static final String RD = "┏";

   public static final String ld = "┐";
   public static final String Ld = "┑";
   public static final String lD = "┒";
   public static final String LD = "┓";

   public static final String ur = "└";
   public static final String uR = "┕";
   public static final String Ur = "┖";
   public static final String UR = "┗";

   public static final String ul = "┘";
   public static final String uL = "┙";
   public static final String Ul = "┚";
   public static final String UL = "┛";

   public static final String vr =  "├";
   public static final String vR =  "┝";
   public static final String Urd = "┞";
   public static final String urD = "┟";
   public static final String Vr =  "┠";
   public static final String URd = "┡";
   public static final String uRD = "┢";
   public static final String VR = "┣";
      public static final String urd = "├";
      public static final String uRd = "┝";
      public static final String UrD = "┠";
      public static final String URD = "┣";

   public static final String vl =  "┤";
   public static final String vL =  "┥";
   public static final String Uld = "┦";
   public static final String ulD = "┧";
   public static final String Vl =  "┨";
   public static final String ULd = "┩";
   public static final String uLD = "┪";
   public static final String VL =  "┫";
      public static final String uld = "┤";
      public static final String uLd = "┥";
      public static final String UlD = "┨";
      public static final String ULD = "┫";

   public static final String hd =  "┬";
   public static final String Lrd = "┭";
   public static final String lRd = "┮";
   public static final String Hd =  "┯";
   public static final String hD =  "┰";
   public static final String LrD = "┱";
   public static final String lRD = "┲";
   public static final String HD =  "┳";
      public static final String lrd = "┬";
      public static final String LRd = "┯";
      public static final String lrD = "┰";
      public static final String LRD = "┳";

   public static final String uh =  "┴";
   public static final String uLr = "┵";
   public static final String ulR = "┶";
   public static final String uH =  "┷";
   public static final String Uh =  "┸";
   public static final String ULr = "┹";
   public static final String UlR = "┺";
   public static final String UH =  "┻";
      public static final String ulr = "┴";
      public static final String uLR = "┷";
      public static final String Ulr = "┸";
      public static final String ULR = "┻";

   public static final String vh =   "┼";
   public static final String vLr =  "┽";
   public static final String vlR =  "┾";
   public static final String vH =   "┿";
   public static final String Uhd =  "╀";
   public static final String uhD =  "╁";
   public static final String Vlr =  "╂";
   public static final String ULrd = "╃";
   public static final String UlRd = "╄";
   public static final String uLrD = "╅";
   public static final String ulRD = "╆";
   public static final String UHd =  "╇";
   public static final String uHD =  "╈";
   public static final String VLr =  "╉";
   public static final String VlR =  "╊";
   public static final String VH =   "╋";
      public static final String ulrd = "┼";
      public static final String uLrd = "┽";
      public static final String ulRd = "┾";
      public static final String uLRd = "┿";
      public static final String Ulrd = "╀";
      public static final String ulrD = "╁";
      public static final String UlrD = "╂";
      public static final String ULRd = "╇";
      public static final String uLRD = "╈";
      public static final String ULrD = "╉";
      public static final String UlRD = "╊";
      public static final String ULRD = "╋";

   public static final String h2 = "╌";
   public static final String H2 = "╍";
   public static final String v2 = "╎";
   public static final String V2 = "╏";

   public static final String hh = "═";
   public static final String vv = "║";
      public static final String llrr = "═";
      public static final String uudd = "║";

   public static final String rrd =  "╒";
   public static final String rdd =  "╓";
   public static final String rrdd = "╔";

   public static final String lld =  "╕";
   public static final String ldd =  "╖";
   public static final String lldd = "╗";

   public static final String urr =  "╘";
   public static final String uur =  "╙";
   public static final String uurr = "╚";

   public static final String ull =  "╛";
   public static final String uul =  "╜";
   public static final String uull = "╝";

   public static final String vrr =  "╞";
   public static final String vvr =  "╟";
   public static final String vvrr = "╠";
      public static final String urrd =   "╞";
      public static final String uurdd =  "╟";
      public static final String uurrdd = "╠";
   
   public static final String vll =  "╡";
   public static final String vvl =  "╢";
   public static final String vvll = "╣";
      public static final String ulld =   "╡";
      public static final String uuldd =  "╢";
      public static final String uulldd = "╣";

   public static final String hhd =  "╤";
   public static final String hdd =  "╥";
   public static final String hhdd = "╦";
      public static final String llrrd =  "╤";
      public static final String lrdd =   "╥";
      public static final String llrrdd = "╦";

   public static final String uhh =  "╧";
   public static final String uuh =  "╨";
   public static final String uuhh = "╩";
      public static final String ullrr =  "╧";
      public static final String uulr =   "╨";
      public static final String uullrr = "╩";

   public static final String vhh =  "╪";
   public static final String vvh =  "╫";
   public static final String vvhh = "╬";
      public static final String ullrrd =   "╪";
      public static final String uulrdd =   "╫";
      public static final String uullrrdd = "╬";

   public static final String r_d = "╭";
   public static final String l_d = "╮";
   public static final String l_u = "╯";
   public static final String r_u = "╰";

   public static final String slash = "╱";
   public static final String bSlash = "╲";
   public static final String X = "╳";

   public static final String l = "╴";
   public static final String u = "╵";
   public static final String r = "╶";
   public static final String d = "╷";

   public static final String L = "╸";
   public static final String U = "╹";
   public static final String R = "╺";
   public static final String D = "╻";

   public static final String lR = "╼";
   public static final String uD = "╽";
   public static final String Lr = "╾";
   public static final String Ud = "╿";
}