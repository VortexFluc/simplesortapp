package com.vortexfluc;

import com.vortexfluc.commandLine.AppCommandLine;
import com.vortexfluc.context.ApplicationContext;
import com.vortexfluc.strings.service.StringService;
import com.vortexfluc.strings.service.CounterStringServiceImpl;
import org.apache.commons.cli.ParseException;

import java.util.*;

/**
 * Класс с входной точкой приложения.
 * Рассматривается в качестве "контроллера" приложения.
 * Таким образом он лишь обрабатывает "запрос" и делегирует выполнение остальным компонентам приложения.
 * */
public class Application {

    /*
    * Входная точка приложения.
    * */
    public static void main(String[] args) {
        // Все флаги в приложении обязательны. Поэтому если какого-то не будет - сообщить пользователю об этом (и показать usage).
        if (args.length < 6) {
            System.out.println("You must specify flags: -i, -o, -sm! All flags are mandatory!");
            AppCommandLine.printHelp();
            return;
        }

        // Считаем все аргументы, которые ввёл пользователь. Если пользователь накосячил - сообщить об этом (и опять показать usage).
        AppCommandLine commandLine;
        try {
            commandLine = new AppCommandLine(args);
        } catch (ParseException e) {
            System.out.println("Unable to parse arguments!");
            AppCommandLine.printHelp();
            return;
        }

        // Получаем контекст приложения, для делегирования его дальше сервису.
        ApplicationContext context = commandLine.getAppContext();

        // Инициализация сервиса.
        StringService service = new CounterStringServiceImpl(context);

        // Получаем отсортированный список. Если не получили - программа сообщит об ошибке и завершит выполнение.
        List<String> sortedStrings = service.getSortedStrings();
        if (sortedStrings == null) return;

        // Сохраняем результат работы нашей программы.
        service.saveStrings(sortedStrings);
    }
}