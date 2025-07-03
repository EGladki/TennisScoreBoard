package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.exception.DatabaseOperationException;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed get tennis matches from database", e);
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
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed get tennis matches with current player from database.", e);
        }
    }

    public int getCountOfMatches() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Long l = session.createQuery("""
                            SELECT count(m) FROM Match m
                            
                            """, Long.class)
                    .getSingleResult();
            return l.intValue();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed get count of tennis matches from database.", e);
        }
    }

    public int getCountOfMatchesWithCurrentPlayer(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Long l = session.createQuery("""
                            SELECT count(m) FROM Match m
                            WHERE m.player1.name = :name OR m.player2.name = :name
                            """, Long.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return l.intValue();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed get count of tennis matches with current player from database.", e);
        }
    }

    public void save(Match match) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DatabaseOperationException("Failed to save tennis match in database.", e);
        }
    }

}
