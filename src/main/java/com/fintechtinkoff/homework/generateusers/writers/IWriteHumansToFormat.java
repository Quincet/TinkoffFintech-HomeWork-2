package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;

import java.util.List;

public interface IWriteHumansToFormat{
    String commonDirectory = System.getProperty("user.dir");
    void writeHumans(List<Human> humans);
}
