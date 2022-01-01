package com.github.pirasleandro.io;


import static com.github.pirasleandro.tweaks.HashSetUtils.toCollection;

import java.util.ArrayList;
import java.util.HashSet;

import com.github.pirasleandro.characters.Box;

public class TablePrinter {

    public enum Meta {
        NULL,
        LIGHT_HEADER,
        HEAVY_HEADER,
        DOUBLE_HEADER,
        DOUBLE_DASHED_HEADER,
        TRIPLE_DASHED_HEADER,
        QUADRUPLE_DASHED_HEADER,
        ROUNDED_CORNERS,
    }
    
    public static <T> String build(T[][] input, Meta... metaArray) {
        @SuppressWarnings("unused") // TODO
        HashSet<Meta> meta = new HashSet<>(toCollection(metaArray));
        String[][] table = stringArrayOf(input);
        int[] minWidths = getMinWidths(table);
        String[] content = new String[table.length];
        String[] fixedDashes = new String[table[0].length];
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[0].length; col++) {
                String current = table[row][col];
                if (current.length() < minWidths[col]) {
                    table[row][col] = current + " ".repeat(minWidths[col]-current.length());
                }
            }
            content[row] = buildRow(Box.v, " ", table[row], Box.v, Box.v);
        }
        for (int col = 0; col < table[0].length; col++) {
            fixedDashes[col] = Box.h.repeat(minWidths[col]);
        }
        return buildRow(
            buildRow(Box.dr, Box.h, fixedDashes, Box.dh, Box.dl),
            "\n",
            content,
            buildRow(Box.vr, Box.h, fixedDashes, Box.vh, Box.vl),
            buildRow(Box.ur, Box.h, fixedDashes, Box.uh, Box.ul)
        );
    }

    public static <T> String build(T[][] input) {
        return build(input, Meta.NULL);
    }

    public static <T> void print(T[][] input, Meta... metaArray) {
        System.out.println(build(input, metaArray));
    }

    public static <T> void print(T[][] input) {
        System.out.println(build(input));
    }

    /**
     * <p> Builds a String that represents a row of a table </p>
     * <i> Example: </i>
     * <p> {@code start = "(" space = "-" content = [1,2,3] divide = "|" end = ")"} </p>
     * <p> {@code output: "(-1-|-2-|-3-)"} </p>
     * @param start start of the row
     * @param space string between {@code start}/{@code divide}/{@code end} and the content
     * @param content string between the dividers
     * @param divide string between the content
     * @param end 
     * @return
     */
    public static String buildRow(String start, String space, String[] content, String divide, String end) {
        StringBuilder output = new StringBuilder();
        output.append(start);
        for (int i = 0; i < content.length; i++) {
            output.append(space + content[i] + space);
            if (i+1 < content.length) {
                output.append(divide);
            }
        }
        output.append(end);
        return output.toString();
    }

    public static String buildRow(String start, String space, ArrayList<String> content, String divide, String end) {
        String[] contentArray = new String[content.size()];
        for (int i = 0; i < content.size(); i++) {
            contentArray[i] = content.get(i);
        }
        return buildRow(start, space, contentArray, divide, end);
    }

    public static String buildRow(String start, String space, String fill, String divide, String end, int cols) {
        ArrayList<String> content = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            content.add(fill);
        }
        return buildRow(start, space, content, divide, end);
    }

    private static int[] getMinWidths(String[][] table) {
        int[] minWidths = new int[table[0].length];
        for (int i = 0; i < table[0].length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (minWidths[i] < table[j][i].length()) {
                    minWidths[i] = table[j][i].length();
                }
            }
        }
        return minWidths;
    }

    public static <T> String[][] stringArrayOf(T[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        String[][] output = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output[i][j] = String.valueOf(array[i][j]);
            }
        }
        return output;
    }
}
