package com.vortexfluc.comparators;

import com.vortexfluc.comparators.enums.SortMethods;

import java.util.Comparator;

public class CustomComparatorFactory {
    public static Comparator<String> getComparatorBySortMethod(SortMethods method, int wordNum) {
        switch (method) {
            case BY_LETTER_COUNT -> {
                return new LetterCountComparator();
            }
            case BY_WORD_NUM -> {
                return new WordNumComparator(wordNum);
            }
            default -> {
                return new LexigraphicalComparator();
            }
        }
    }

    public static Comparator<String> getComparatorBySortMethod(SortMethods method) {
        return getComparatorBySortMethod(method, 1);
    }
}
