package com.vortexfluc.strings.service;

import com.vortexfluc.context.ApplicationContext;
import com.vortexfluc.strings.ApplicationConstants;
import com.vortexfluc.strings.comparators.CustomComparatorFactory;
import com.vortexfluc.strings.comparators.enums.SortMethods;
import com.vortexfluc.strings.dao.StringsDao;
import com.vortexfluc.strings.dao.StringsDaoFileImpl;

import java.util.*;

/**
 * Вся "бизнес" логика приложения.
 * */
public class CounterStringServiceImpl implements StringService, StringCounter {
    private final ApplicationContext context;
    private final StringsDao dao;
    private final Map<String, Integer> stringCounter;

    public CounterStringServiceImpl(ApplicationContext context) {
        this.context = context;
        dao = new StringsDaoFileImpl(context.getInputFilename(), context.getOutputFilename());
        stringCounter = new HashMap<>();
    }

    @Override
    public List<String> getStrings() {
        return dao.getAllString();
    }

    @Override
    public List<String> getSortedStrings() {
        List<String> inputFileLines = dao.getAllString();

        // Получаем минимальное (общую) количество слов. Чтобы контролировать ситуацию OutOfBounds.
        var minWords = getMinWords(inputFileLines);

        int wordNum = ApplicationConstants.DEFAULT_WORD_NUM;
        if (context.getSortMethod().equals(SortMethods.BY_WORD_NUM)) {
            try {
                wordNum = context.getWordNumber();
            } catch (NumberFormatException nfe) {
                System.out.println("Порядковый номер слова по которому производится сортировка должен быть ЧИСЛОМ!");
                return null;
            }
        }

        // Если общая для всех количество слов равно нулю, то значит существует пустая строка в файле.
        if (wordNum == 0) {
            System.out.println("В файле присутствует пустая строка. Для корректной работы программы необходимо убрать пустые строки из файла!");
            return null;
        }

        // Здесь контролируем OutOfBounds.
        if (wordNum > minWords) {
            System.out.println("Указанный порядковый номер слова [" + wordNum + "] должен быть меньше или равен минимальному количеству слов в строке [" + minWords + "]!");
            return null;
        }

        // Получаем компаратор по методу сортировки
        Comparator<String> comparator = CustomComparatorFactory.getComparatorBySortMethod(context.getSortMethod(), wordNum);

        // Заводим отдельный массив под сортировку
        List<String> sortedStrings = new ArrayList<>(inputFileLines);
        sortedStrings.sort(comparator);
        getStringsCounter(sortedStrings);
        return sortedStrings;
    }

    @Override
    public void saveStrings(List<String> strings) {
        // Генерируем по требованиям строки вида "Исходный вид строки [<количество повторений данной строки>]"
        var resultList = strings.stream().map(el -> el + "[" + stringCounter.get(el) + "].").toList();
        dao.save(resultList);
    }

    @Override
    public Map<String, Integer> getStringsCounter(List<String> strings) {
        strings.forEach(el -> {
            if (stringCounter.containsKey(el)) {
                var oldCounterVal = stringCounter.get(el);
                stringCounter.put(el, oldCounterVal + 1);
            } else {
                stringCounter.put(el, 1);
            }
        });
        return stringCounter;
    }

    private Integer getMinWords(List<String> inputFileLines) {
        var minWordNums = inputFileLines
                .stream()
                .min(Comparator.comparingInt(o -> o.split(" ").length))
                .map(el -> el.split(" ").length);
        return minWordNums.orElse(0);
    }
}
