package com.vortexfluc;

import com.vortexfluc.commandLine.AppCommandLine;
import com.vortexfluc.comparators.CustomComparatorFactory;
import com.vortexfluc.comparators.enums.SortMethods;
import com.vortexfluc.context.ApplicationContext;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Application {
    private static final int DEFAULT_WORD_NUM = 1;

    /*
     * Application entry point.
     *
     * */
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("You must specify flags: -i, -o, -sm!");
            AppCommandLine.printHelp();
            return;
        }

        AppCommandLine commandLine;
        try {
            commandLine = new AppCommandLine(args);
        } catch (ParseException e) {
            System.out.println("Unable to parse arguments!");
            AppCommandLine.printHelp();
            return;
        }

        ApplicationContext context = commandLine.getAppContext();

        Map<String, Integer> stringOccurences = new HashMap<>();
        List<String> sortedStrings = getStrings(context, stringOccurences);
        if (sortedStrings == null) return;

        writeResultFile(context, sortedStrings, stringOccurences);
    }

    private static List<String> getStrings(ApplicationContext context, Map<String, Integer> stringOccurences) {
        List<String> sortedStrings;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(context.getInputFilename()))) {
            // За один проход подсчитываем количество строк и формируем немутабельный лист.
            var inputFileLines = bufferedReader.lines().map(el -> {
                if (stringOccurences.containsKey(el)) {
                    int prevValue = stringOccurences.get(el);
                    stringOccurences.put(el, prevValue + 1);
                } else {
                    stringOccurences.put(el, 1);
                }
                return el;
            }).toList();

            // Получаем минимальное (общую) количество слов. Чтобы контролировать ситуацию OutOfBounds.
            var minWords = getMinWords(inputFileLines);

            int wordNum = DEFAULT_WORD_NUM;
            if (context.getSortMethod().equals(SortMethods.BY_WORD_NUM)) {
                try {
                    wordNum = context.getWordNumber();
                } catch (NumberFormatException nfe) {
                    System.out.println("Word number must be a NUMBER!");
                    return null;
                }
            }

            // Если общая для всех количество слов равно нулю, то значит существует пустая строка в файле.
            if (wordNum == 0) {
                System.out.println("You passed a file with empty line!");
                return null;
            }

            // Здесь контролируем OutOfBounds.
            if (wordNum > minWords) {
                System.out.println("Word num [" + wordNum + "] needs to be less or equal then minimum word count [" + minWords + "]!");
                return null;
            }

            // Получаем компаратор по методу сортировки
            Comparator<String> comparator = CustomComparatorFactory.getComparatorBySortMethod(context.getSortMethod(), wordNum);

            // Заводим отдельный массив под сортировку
            sortedStrings = new ArrayList<>(inputFileLines);
            sortedStrings.sort(comparator);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
            return null;
        } catch (IOException e) {
            System.out.println("IOException! Something went wrong!");
            return null;
        }
        return sortedStrings;
    }

    /*
    * Function publish the result file
    * */
    private static void writeResultFile(ApplicationContext context, List<String> sortedStrings, Map<String, Integer> stringOccurences) {
        // Генерируем по требованиям строки вида "Исходный вид строки [<количество повторений данной строки>]"
        var resultList = sortedStrings.stream().map(el -> el + "[" + stringOccurences.get(el) + "].").collect(Collectors.toList());

        try {
            Files.write(Path.of(context.getOutputFilename()), resultList, StandardCharsets.UTF_8);
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Unable to create output file!");
        }
    }

    /*
     * Function return minimal (common) word length.
     * */
    private static Integer getMinWords(List<String> inputFileLines) {
        var minWordNums = inputFileLines
                .stream()
                .min(Comparator.comparingInt(o -> o.split(" ").length))
                .map(el -> el.split(" ").length);
        return minWordNums.orElse(0);
    }
}