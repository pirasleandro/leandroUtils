package com.github.pirasleandro.io;


import static com.github.pirasleandro.tweaks.HashSetUtils.toCollection;

import java.util.ArrayList;
import java.util.HashSet;

import com.github.pirasleandro.characters.Box;

public class TablePrinter {

    public enum LineType {
        LIGHT,
        HEAVY,
        DOUBLE,
        DOUBLE_DASHED,
        TRIPLE_DASHED,
        QUADRUPLE_DASHED
    }
    
    /**
     * Returns a String representing the input as a table. May be modified with meta data.
     * @param <T> input type
     * @param input 2D array containing the values that will be represented as a table
     * @param meta {@code HashSet<Meta>} containing {@code Meta} values to modify the table
     * @return the built string
     * @see #build(Object[][])
     */
    public static <T> String build(T[][] input, LineType header, LineType border) {
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

    /**
     * Returns a String representing the input as a table.
     * @param <T> input type
     * @param input 2D array containing the values that will be represented as a table
     * @return the built string
     * @apiNote all meta data will be set to {@code LineType.LIGHT}
     * @see #build(Object[][], Meta...)
     */
    public static <T> String build(T[][] input) {
        return build(input, LineType.LIGHT, LineType.LIGHT);
    }

    /**
     * Prints a String representing the input as a table.
     * @param <T> input type
     * @param input 2D array containing the values that will be represented as a table
     * @apiNote all meta data will be set to {@code LineType.LIGHT}
     * @see #build(Object[][])
     */
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

    /**
     * <p>Builds a single row of a table.</p>
     * <p>structure: start + (space + content + space + divide)* + space + end</p>
     * <p>*repeat for every element in {@code content}</p>
     * @param start first string
     * @param space string between values and start/divide/end
     * @param content values represented in this row
     * @param divide string between values
     * @param end last string
     * @return the built string
     */
    public static String buildRow(String start, String space, ArrayList<String> content, String divide, String end) {
        String[] contentArray = new String[content.size()];
        for (int i = 0; i < content.size(); i++) {
            contentArray[i] = content.get(i);
        }
        return buildRow(start, space, contentArray, divide, end);
    }

    /**
     * <p>Builds a single row of a table. Intended to be used to divide content rows.</p>
     * <p>structure: start + (space + fill + space + divide)* + space + end</p>
     * <p>*repeat {@code col} times</p>
     * @param start first string
     * @param space string between fill and start/divide/end
     * @param fill string replacing the usual content
     * @param divide string between fill strings
     * @param end last string
     * @param cols amount of columns
     * @return the built string
     */
    public static String buildRow(String start, String space, String fill, String divide, String end, int cols) {
        ArrayList<String> content = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            content.add(fill);
        }
        return buildRow(start, space, content, divide, end);
    }

    public enum CharType {
        V, H,
        UL, UR, DL, DR,
        UH, HD, VL, VR,
        VH;
    }

    /**
     * Calculates the min width needed for every column of a table to represent it properly
     * @param table table whose columns min widths are needed
     * @return {@code int[]} containing all min widths
     */
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

    /**
     * Converts any 2D array to a {@code String[][]}
     * @param <T> type of input array
     * @param array array that needs to be converted
     * @return converted array
     */
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
