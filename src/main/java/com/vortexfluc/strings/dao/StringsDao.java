package com.vortexfluc.strings.dao;

import java.util.List;

/**
 * ДАОшка для сокрытия реализации доступа к данным. Мало ли, вдруг понадобится тащить данные из БД?
 * */
public interface StringsDao {
    List<String> getAllString();
    void save(List<String> strings);
}
