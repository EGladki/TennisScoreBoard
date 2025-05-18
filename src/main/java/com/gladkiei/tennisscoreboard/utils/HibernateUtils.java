package com.gladkiei.tennisscoreboard.utils;

import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.Player;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try {
            Configuration configuration = new Configuration().addAnnotatedClass(Match.class)
                    .addAnnotatedClass(Player.class);
            return configuration.buildSessionFactory();

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        SESSION_FACTORY.close();
    }

}
