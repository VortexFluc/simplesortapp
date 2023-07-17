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
        Option inputFile = new Option("i", "inputFile", true, "Название (абсолютное или относительное) файла с исходными строками.");
        inputFile.setRequired(true);

        Option outputFile = new Option("o", "outputFile", true, "Желаемое название выходного файла.");
        inputFile.setRequired(true);

        Option sortMethod = new Option("sm", "sortMethod", true, "Метод сортировки. [1, 2 или 3].\n" +
                "1 - Лексикографический.\n" +
                "2 - По количеству символов в строке.\n" +
                "3 - По указанному в аргументе порядковому номеру слова (Лексикографически).\n");
        inputFile.setRequired(false);

        Option help = new Option("h", "help", false, "Описание флагов приложения.");
        help.setRequired(false);

        options.addOption(inputFile);
        options.addOption(outputFile);
        options.addOption(sortMethod);
        options.addOption(help);
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
        formatter.printHelp(
                "java -jar SimpleSortApp-1.0-SNAPSHOT-jar-with-dependencies.jar -i <input_file_name>" +
                        " -o <output_file_name> -sm [1|2|3] <word_number>",
                options);
    }
}
