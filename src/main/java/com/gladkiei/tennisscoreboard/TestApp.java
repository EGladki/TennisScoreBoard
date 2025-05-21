package com.gladkiei.tennisscoreboard;

import com.gladkiei.tennisscoreboard.dao.OngoingMatchDao;

import java.util.UUID;


public class TestApp {
    public static void main(String[] args) {

//        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//
//        session.createQuery("FROM Player", Player.class).getResultStream()
//                .forEach(System.out::println);
//
//        session.createQuery("FROM Match", Match.class).getResultStream()
//                .forEach(System.out::println);
//
//
//        session.getTransaction().commit();
//        HibernateUtils.shutdown();


        OngoingMatchDao storage = OngoingMatchDao.getInstance();

        UUID uuid = UUID.randomUUID();
//        storage.put(uuid, new OngoingMatch(1L, new Player("Bob"), new Player("Tom"), 1L, 2L));
        System.out.println(storage.getMatch(uuid));
    }

}
