package com.github.pirasleandro.io;

import java.util.HashMap;
import java.util.Map;

import com.github.pirasleandro.characters.Box;

public class BigPrinter<T> {
    private final int height;
    private HashMap<T, String[]> map = new HashMap<>();

    public static final HashMap<Integer, String[]> digits = (HashMap<Integer, String[]>) Map.of(
        0, new String[]{Box.dr + Box.dl, Box.v  + Box.v,  Box.ur + Box.ul},
        1, new String[]{" "    + Box.dl, " "    + Box.v,  " "    + Box.u },
        2, new String[]{Box.dr + Box.dl, Box.dr + Box.ul, Box.ur + Box.l },
        3, new String[]{Box.dr + Box.dl, " "    + Box.vl, Box.ur + Box.ul},
        4, new String[]{Box.d  + Box.d,  Box.ur + Box.vl, " "    + Box.u },
        5, new String[]{Box.dr + Box.l,  Box.ur + Box.dl, Box.ur + Box.ul},
        6, new String[]{Box.dr + Box.dl, Box.vr + Box.dl, Box.ur + Box.ul},
        7, new String[]{Box.r  + Box.dl, " "    + Box.v,  " "    + Box.u },
        8, new String[]{Box.dr + Box.dl, Box.vr + Box.vl, Box.ur + Box.ul},
        9, new String[]{Box.dr + Box.dl, Box.ur + Box.vl, Box.ur + Box.ul}
    );

    public BigPrinter(int height) {
        this.height = height;
    }

    public BigPrinter(HashMap<T, String[]> map) {
        height = ((String[]) map.values().toArray()[0]).length;
        for (int i = 0; i < map.size(); i++) {
            String[] current = (String[]) map.values().toArray()[i];
            if (current.length != height) {
                throw new IllegalArgumentException("All elements must have the same height.");
            }
        }
        this.map = map;
    }

    public void addEntry(T key, String[] value) {
        if (value.length != height) {
            throw new IllegalArgumentException("All elements must have the same height.");
        }
        map.put(key, value);
    }

    public void removeEntry(T key) {
        map.remove(key);
    }

    public String build(int space, T[] input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < map.size(); j++) {
                sb.append(map.get(input[j])[i]);
                if (j+1 < map.size()) {
                    sb.append(" ".repeat(space));
                }
            }
            if (i+1 < map.size()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String build(T[] input) {
        return build(1, input);
    }

    public String build(T input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sb.append(map.get(input)[i]);
            if (i+1 < map.size()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void print(int space, T[] input) {
        System.out.println(build(space, input));
    }

    public void print(T[] input) {
        System.out.println(build(input));
    }

    public void print(T input) {
        System.out.println(build(input));
    }
}
