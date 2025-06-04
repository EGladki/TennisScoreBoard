package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.service.OngoingMatchService;
import com.gladkiei.tennisscoreboard.validation.NameValidator;
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
    private final NameValidator nameValidator = new NameValidator();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String player1name = req.getParameter("player1");
        nameValidator.validate(player1name);

        String player2name = req.getParameter("player2");
        nameValidator.validate(player2name);

        MatchScoreModel matchScoreModel = ongoingMatchService.startMatch(new PlayerRequestDto(player1name), new PlayerRequestDto(player2name));
        UUID uuid = matchScoreModel.getUuid();

        resp.sendRedirect("match-score" + "?uuid=" + uuid);
    }

}



