package com.github.pirasleandro.tweaks;

import java.util.ArrayList;
import java.util.Collection;

public class HashSetUtils {
    
    public static <T> Collection<T> toCollection(T[] array) {
        Collection<T> output = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            output.add(array[i]);
        }
        return output;
    }
}
