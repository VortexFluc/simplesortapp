package com.vortexfluc.strings.service;

import java.util.List;
import java.util.Map;

/**
 * Опциональное поведение сервиса - подсчёт строк. Вынес в отдельный интерфейс, так как не каждый сервис захочет "считать"
 * строки.
 * */
public interface StringCounter {
    Map<String, Integer> getStringsCounter(List<String> strings);
}
