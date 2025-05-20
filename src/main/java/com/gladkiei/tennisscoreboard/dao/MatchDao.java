package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class MatchDao {

    public List<Match> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {

            return session.createQuery("""
            SELECT m FROM Match m
            JOIN FETCH m.player1
            JOIN FETCH m.player2
            LEFT JOIN FETCH m.winner
            """, Match.class).getResultList();
        }
    }
}
