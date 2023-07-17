package com.vortexfluc.strings.comparators.enums;

/**
 * Enum видов сортировки. Повышает читаемость, понижает забывчиваость в switch-case.
 * */
public enum SortMethods {
    LEXICOGRAPHICALLY("1"),
    BY_LETTER_COUNT("2"),
    BY_WORD_NUM("3");
    private String index;

    SortMethods(String index) {
        this.index = index;
    }

    public static SortMethods valueOfIndex(String index) {
        for (var method: SortMethods.values()) {
            if (index.equals(method.index)) {
                return method;
            }
        }
        return LEXICOGRAPHICALLY;
    }
}
