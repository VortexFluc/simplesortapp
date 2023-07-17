package com.vortexfluc.commandLine;

import com.vortexfluc.comparators.enums.SortMethods;
import com.vortexfluc.context.ApplicationContext;
import org.apache.commons.cli.*;

public class AppCommandLine {
    private static final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();
    private final CommandLine commandLine;
    private static final HelpFormatter formatter = new HelpFormatter();
    private final ApplicationContext applicationContext;
    static {
        CommandLine commandLine = null;
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
        var wordNum = 1;
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
