package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import com.gladkiei.tennisscoreboard.utils.MappingUtils;
import org.hibernate.Session;

import java.util.List;


public class PlayerDao {


    public Player save(PlayerRequestDto requestDto) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            Player player = MappingUtils.convertToPlayer(requestDto);
            session.persist(player);
            session.getTransaction().commit();
            return player;
        }
    }

    public List<Player> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("FROM Player", Player.class).getResultList();
        }
    }

    public Player findById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Player.class, id);
        }
    }
}
