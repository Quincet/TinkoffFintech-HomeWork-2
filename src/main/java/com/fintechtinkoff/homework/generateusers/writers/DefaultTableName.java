package com.fintechtinkoff.homework.generateusers.writers;

import java.util.ArrayList;
import java.util.List;

public final class DefaultTableName {
    public static List<String> getNameOfColumns() {
        var list = new ArrayList<String>();
        list.add("№");
        list.add("Имя");
        list.add("Фамилия");
        list.add("Отчество");
        list.add("Возраст");
        list.add("Пол");
        list.add("Дата рождения");
        list.add("ИНН");
        list.add("Индекс");
        list.add("Страна");
        list.add("Регион");
        list.add("Город");
        list.add("Улица");
        list.add("Дом");
        list.add("Квартира");
        return list;
    }
    private DefaultTableName() { }
}
