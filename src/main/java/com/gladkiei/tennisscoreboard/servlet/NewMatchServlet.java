package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.service.OngoingMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String player1name = req.getParameter("player1");
        String player2name = req.getParameter("player2");

        MatchScoreModel matchScoreModel = ongoingMatchService.startMatch(new PlayerRequestDto(player1name), new PlayerRequestDto(player2name));
        UUID uuid = matchScoreModel.getUuid();

        resp.sendRedirect("match-score" + "?uuid=" + uuid);
    }

}



