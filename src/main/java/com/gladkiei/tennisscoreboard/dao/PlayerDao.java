package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.exception.DatabaseOperationException;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PlayerDao {

    public void save(Player player) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DatabaseOperationException("Failed to save player in database", e);
        }
    }

    public List<Player> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            List<Player> players = session.createQuery("FROM Player order by id", Player.class).getResultList();

            session.getTransaction().commit();
            return players;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get players from database", e);
        }
    }

    public Player getById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Player player = session.find(Player.class, id);

            session.getTransaction().commit();
            return player;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get player by id from database", e);
        }
    }

    public Optional<Player> getByName(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Player> query = session.createQuery("FROM Player WHERE name=:name", Player.class);
            query.setParameter("name", name);
            query.setMaxResults(1);

            Optional<Player> playerOptional = query.uniqueResultOptional();

            session.getTransaction().commit();
            return playerOptional;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get player by name from database", e);
        }
    }

}
