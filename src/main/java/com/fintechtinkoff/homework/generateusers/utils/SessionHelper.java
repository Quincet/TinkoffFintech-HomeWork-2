package com.fintechtinkoff.homework.generateusers.utils;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionHelper {
    public static Session getSession() {
        return new MetadataSources(
                new StandardServiceRegistryBuilder()
                .configure()
                .build())
                .buildMetadata()
                .buildSessionFactory()
                .openSession();
    }
    private SessionHelper() { }
}
