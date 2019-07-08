package com.fintechtinkoff.homework.generateusers.factory;

import com.fintechtinkoff.homework.generateusers.human.Human;

import java.util.List;

public interface IHumanFactory {
    Human createHuman() throws Exception;

    List<Human> createHumans(int countHumans) throws Exception;
}
