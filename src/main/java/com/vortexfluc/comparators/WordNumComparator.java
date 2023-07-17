package com.vortexfluc.comparators;

import java.util.Comparator;

public class WordNumComparator implements Comparator<String> {
    private int wordNum;
    public WordNumComparator(int wordNum) {
        this.wordNum = wordNum;
    }

    @Override
    public int compare(String o1, String o2) {
        return o1.split(" ")[wordNum - 1].compareTo(o2.split(" ")[wordNum - 1]);
    }
}
