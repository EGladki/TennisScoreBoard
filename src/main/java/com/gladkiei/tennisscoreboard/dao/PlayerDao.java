package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
            session.beginTransaction();

            List<Player> players = session.createQuery("FROM Player", Player.class).getResultList();

            session.getTransaction().commit();
            return players;
        }
    }

    public Player findById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Player player = session.find(Player.class, id);

            session.getTransaction().commit();
            return player;
        }
    }

    public Optional<Player> findByName(String name) {
//        if (name == null || name.isBlank()) {
//            return Optional.empty();
//        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Player> query = session.createQuery("FROM Player WHERE name=:name", Player.class);
            query.setParameter("name", name);
            query.setMaxResults(1);

            Optional<Player> playerOptional = query.uniqueResultOptional();

            session.getTransaction().commit();
            return playerOptional;
        }
    }

}
