package com.gmail.kiiiiiim1005.diary.util;

import com.gmail.kiiiiiim1005.diary.Privates;
import com.gmail.kiiiiiim1005.diary.entity.Diary;
import com.gmail.kiiiiiim1005.diary.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ThreadLocal<Session> localSession = new ThreadLocal<>();

    public static void buildSessionFactory() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Configuration configuration = getHibernateConfigByCode();
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
        configuration.buildSessionFactory(serviceRegistryBuilder.build());
        System.out.println("Build SessionFactory");
    }

    private static Configuration getHibernateConfigByCode() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Diary.class);

        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        configuration.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/javalindiary?user=root&password="+ Privates.dbPassword + "&characterEncoding=UTF-8&serverTimezone=UTC");
        configuration.setProperty(Environment.USER, "root");
        configuration.setProperty(Environment.POOL_SIZE, "55");
        configuration.setProperty(Environment.STATEMENT_BATCH_SIZE, "30");
        configuration.setProperty(Environment.AUTOCOMMIT, "false");
        configuration.setProperty(Environment.SHOW_SQL, "true");
        configuration.setProperty(Environment.FORMAT_SQL, "true");
        configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperty(Environment.HBM2DDL_AUTO, "update");
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getLocalSession() {
        Session session = localSession.get();
        if (session == null) {
            session = sessionFactory.openSession();
            localSession.set(session);
        }
        return session;
    }

    public static void closeLocalSession() {
        Session session = localSession.get();
        if (session != null) session.close();
        localSession.set(null);
    }

}
