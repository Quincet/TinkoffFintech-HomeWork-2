package com.fintechtinkoff.homework.generateusers.writers;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.SessionHelper;
import org.hibernate.Session;

import java.util.List;

public final class DataBaseWriter implements IWriteHumansToFormat {
    @Override
    public void writeHumans(final List<Human> humans) {
        try (Session session = SessionHelper.getSession()) {
            for (Human human : humans) {
                session.save(human);
            }
        } catch (Exception e) {
            System.out.println("Не удалось записать данные в базу данных \n\n");
            e.printStackTrace();
        }
    }
}
