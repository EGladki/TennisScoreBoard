package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PlayerDao {

    public void save(Player player) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.persist(player);
            session.getTransaction().commit();
        }
    }

    public List<Player> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player", Player.class).getResultList();
        }
    }

    public Player findById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.find(Player.class, id);
        }
    }

    public Optional<Player> findByName(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            return (session.createQuery("FROM Player WHERE name=:name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional());
        }
    }

}
