package com.github.pirasleandro.io;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StringGenerator {
    private static final String SLASH = "\\\\";
    private static final String QUOT_MARK = "\\\"";
    private static final String TAB = " ".repeat(4);
    private static final String NEW_LINE = "\"\\n\"";
    
    public static void generate(File file) throws IOException {
        Scanner scan = new Scanner(file);
        StringBuilder sb = new StringBuilder("String generated =\n");
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.length() > 0) {
                sb.append("\"" + line.replace("\\", SLASH).replace("\"", QUOT_MARK).replace("\t", TAB) + "\"");
            }
            sb.append(scan.hasNextLine() ? (line.length() > 0 ? " + " : "") + NEW_LINE + " +\n" : ";");
        }
        System.out.println(sb.toString());
        scan.close();
    }
}
