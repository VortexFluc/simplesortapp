package com.vortexfluc.strings.comparators;

import java.util.Comparator;

/**
 * Компаратор лексикографический. В принципе это дефолтная реализация у String, но лучше явно указать.
 * */
public class LexigraphicalComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
