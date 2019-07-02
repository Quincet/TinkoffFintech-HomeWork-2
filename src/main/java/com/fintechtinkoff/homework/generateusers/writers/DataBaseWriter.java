package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.SessionHelper;
import org.hibernate.Session;

import java.util.List;

public class DataBaseWriter implements IWriteHumansToFormat{
    @Override
    public void writeHumans(List<Human> humans) {
        try(Session session = SessionHelper.getSeesion()) {
            for (Human human : humans) {
                session.save(human);
            }
        } catch (Exception e){
            System.out.println("Не удалось записать данные в базу данных \n\n");
            e.printStackTrace();
        }
    }
}
