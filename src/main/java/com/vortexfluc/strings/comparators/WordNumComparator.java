package com.vortexfluc.strings.comparators;

import java.util.Comparator;

/**
 * Самый навороченный компаратор проекта - по выбранному слову.
 * */
public class WordNumComparator implements Comparator<String> {
    private int wordNum; // Номер слова в строке.
    public WordNumComparator(int wordNum) {
        this.wordNum = wordNum;
    }

    // Сами по себе строки сортируются лексикографически. Просто мы учитываем в сортировке какое-то определённое слово.
    @Override
    public int compare(String o1, String o2) {
        return o1.split(" ")[wordNum - 1].compareTo(o2.split(" ")[wordNum - 1]);
    }
}
