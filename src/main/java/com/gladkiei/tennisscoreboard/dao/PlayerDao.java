package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.HibernateUtils;
import com.gladkiei.tennisscoreboard.utils.MappingUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerDao {

    public Player save(PlayerRequestDto requestDto) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Player player = MappingUtils.convertToPlayer(requestDto);

            session.persist(player);
            session.getTransaction().commit();

            return player;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Player> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            return session.createQuery("FROM Player", Player.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
