package com.vortexfluc.commandLine;

import com.vortexfluc.strings.comparators.enums.SortMethods;
import com.vortexfluc.context.ApplicationContext;
import org.apache.commons.cli.*;

/*
* Класс отвечает за взаимодействие с аргументами командной строки.
* */
public class AppCommandLine {
    private static final Options options = new Options(); // Содержит флаги.
    private final CommandLineParser parser = new DefaultParser(); // Парсер аргументов (отделяет объявление флага от значения)
    private final CommandLine commandLine; // Сама командная строка.
    private static final HelpFormatter formatter = new HelpFormatter(); // Данный объект поможет отформатировать help.
    private final ApplicationContext applicationContext; // Здесь хранится "контекст" приложения (inputFile, outputFile..)
    private static final int DEFAULT_WORD_NUM = 1;

    static {
        Option inputFile = new Option("i", "inputFile", true, "Name (absolute or relative) of an input file.");
        inputFile.setRequired(true);

        Option outputFile = new Option("o", "outputFile", true, "Desired name of output file.");
        inputFile.setRequired(true);

        Option sortMethod = new Option("sm", "sortMethod", true, "Sort method. [1, 2 or 3]");
        inputFile.setRequired(false);

        options.addOption(inputFile);
        options.addOption(outputFile);
        options.addOption(sortMethod);
    }

    public AppCommandLine(String[] args) throws ParseException {
        this.commandLine = parser.parse(options, args);
        var arguments = commandLine.getArgs();

        // Если пользователь забыл указать порядковый номер слова при сортировке по слову, то будет сортировать по первому.
        var wordNum = DEFAULT_WORD_NUM;
        if (arguments.length > 0) {
            wordNum = Integer.parseInt(arguments[0]);
        }
        applicationContext = new ApplicationContext(
                commandLine.getOptionValue("i"),
                commandLine.getOptionValue("o"),
                SortMethods.valueOfIndex(commandLine.getOptionValue("sm")),
                wordNum
        );
    }

    public ApplicationContext getAppContext() {
        return this.applicationContext;
    }

    public static void printHelp() {
        formatter.printHelp("utility-name", options);
    }
}
