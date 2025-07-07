package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.exceptions.DatabaseOperationException;
import com.gladkiei.tennisscoreboard.models.common.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            return session.createQuery("FROM Player order by id", Player.class).getResultList();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get players from database", e);
        }
    }

    public Player getById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Player.class, id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get player by id from database", e);
        }
    }

    public Optional<Player> getByName(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player WHERE name=:name", Player.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get player by name from database", e);
        }
    }

}
