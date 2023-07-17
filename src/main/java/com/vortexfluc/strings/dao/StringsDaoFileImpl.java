package com.vortexfluc.strings.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация ДАО, где источником данных является файл.
 * */
public class StringsDaoFileImpl implements StringsDao {

    private String inputFilename;
    private Path outputFilename;

    public StringsDaoFileImpl(String inputFilename, String outputFilename) {
        this.inputFilename = inputFilename;
        this.outputFilename = Path.of(outputFilename);
    }

    @Override
    public List<String> getAllString() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilename))) {
            return bufferedReader.lines().toList();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден в системе!");
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<String> strings) {
        try {
            Files.write(outputFilename, strings, StandardCharsets.UTF_8);
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Unable to create output file!");
        }
    }
}
