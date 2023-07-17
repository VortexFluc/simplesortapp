package com.vortexfluc.strings.service;

import com.vortexfluc.context.ApplicationContext;

import java.util.List;

/**
 * Базовый сервис "строк". Необходим для инкапсулирования логики, дабы в дальнейшем можно было подменить какой-либо
 * другой реализацией
 * */
public interface StringService {
    List<String> getStrings();
    List<String> getSortedStrings();
    void saveStrings(List<String> strings);
}
