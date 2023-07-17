package com.vortexfluc.context;

import com.vortexfluc.comparators.enums.SortMethods;

public class ApplicationContext {
    private final String inputFilename;
    private final String outputFilename;
    private final SortMethods sortMethod;
    private final int wordNumber;

    public ApplicationContext(String inputFilename, String outputFilename, SortMethods sortMethod, int wordNumber) {
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
        this.sortMethod = sortMethod;
        this.wordNumber = wordNumber;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public SortMethods getSortMethod() {
        return sortMethod;
    }

    public int getWordNumber() {
        return wordNumber;
    }
}
