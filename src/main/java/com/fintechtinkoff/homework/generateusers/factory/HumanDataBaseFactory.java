package com.fintechtinkoff.homework.generateusers.factory;

import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.utils.SessionHelper;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public final class HumanDataBaseFactory implements IHumanFactory {
    @Override
    public Human createHuman()
            throws SQLException {
        return createHumans(1).get(0);
    }

    @Override
    public List<Human> createHumans(int countHumans)
            throws SQLException {
        Session session = SessionHelper.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Human> criteria = builder.createQuery(Human.class);
        criteria.from(Human.class);
        List<Human> data = session
                .createQuery(criteria)
                .setMaxResults(countHumans)
                .getResultList();
        Collections.shuffle(data);
        if (data.isEmpty()) {
            throw new SQLException();
        }
        return data;
    }
}
