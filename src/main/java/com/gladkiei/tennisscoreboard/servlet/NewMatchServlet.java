package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.utils.MappingUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.MappingMatch;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerDao playerDao = new PlayerDao();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String first = req.getParameter("player1");
        String second = req.getParameter("player2");

        PlayerRequestDto playerRequestDto1 = new PlayerRequestDto(first);
        PlayerRequestDto playerRequestDto2 = new PlayerRequestDto(second);

        Player player1 = playerDao.save(playerRequestDto1);
        Player player2 = playerDao.save(playerRequestDto2);

        resp.sendRedirect(req.getContextPath() + "/matches-page.jsp");

    }
}

