package com.gladkiei.tennisscoreboard.utils;

import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.Player;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().addAnnotatedClass(Match.class)
                    .addAnnotatedClass(Player.class);
            return configuration.buildSessionFactory();

        } catch (HibernateException e) {
            throw new RuntimeException("Initial SessionFactory creation failed");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
