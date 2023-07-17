package com.vortexfluc.strings.comparators;

import java.util.Comparator;

/**
 * Компаратор по длине строки.
 * */
public class LetterCountComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.length() - o2.length();
    }
}
