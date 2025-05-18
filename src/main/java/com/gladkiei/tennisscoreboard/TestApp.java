package com.gladkiei.tennisscoreboard;

import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;

public class TestApp {
    public static void main(String[] args) {

        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("FROM Player", Player.class).getResultStream()
                .forEach(System.out::println);

        session.createQuery("FROM Match", Match.class).getResultStream()
                .forEach(System.out::println);


        session.getTransaction().commit();
        HibernateUtils.shutdown();

    }

}
