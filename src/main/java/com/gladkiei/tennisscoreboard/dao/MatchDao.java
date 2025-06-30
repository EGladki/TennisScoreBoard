package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class MatchDao {

    public List<Match> getFiveMatches(int startId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("""
                            SELECT m FROM Match m
                            JOIN FETCH m.player1
                            JOIN FETCH m.player2
                            LEFT JOIN FETCH m.winner
                            WHERE m.id >= :startId
                            ORDER BY m.id
                            """, Match.class)
                    .setParameter("startId", startId)
                    .setMaxResults(5)
                    .getResultList();
        }
    }

    public List<Match> getFiveMatchesWithCurrentPlayer(String name, int startId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("""
                            SELECT m FROM Match m
                            JOIN FETCH m.player1
                            JOIN FETCH m.player2
                            LEFT JOIN FETCH m.winner
                            WHERE m.player1.name = :name OR m.player2.name = :name
                            ORDER BY m.id
                            LIMIT 5
                            """, Match.class)
                    .setParameter("name", name)
                    .setFirstResult(startId - 1)
                    .setMaxResults(5)
                    .getResultList();
        }
    }

    public int getCountOfMatches() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Long l = session.createQuery("""
                            SELECT count (m) FROM Match m
                            
                            """, Long.class)
                    .getResultList()
                    .get(0);
            return l.intValue();
        }
    }

    public void save(Match match) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
        }
    }


}
