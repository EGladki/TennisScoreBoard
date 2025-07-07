package com.gladkiei.tennisscoreboard.servlets;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.exceptions.BadRequestException;
import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;
import com.gladkiei.tennisscoreboard.service.OngoingMatchService;
import com.gladkiei.tennisscoreboard.utils.NameValidationUtils;
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
    private final NameValidationUtils nameValidationUtils = new NameValidationUtils();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String player1name = req.getParameter("player1").trim();
            String player2name = req.getParameter("player2").trim();
            nameValidationUtils.validate(player1name);
            nameValidationUtils.validate(player2name);
            nameValidationUtils.validateNameEquality(player1name, player2name);

            MatchScoreModel matchScoreModel = ongoingMatchService.startAndReturnMatch(new PlayerRequestDto(player1name), new PlayerRequestDto(player2name));
            UUID uuid = matchScoreModel.getUuid();
            resp.sendRedirect("match-score" + "?uuid=" + uuid);

        } catch (BadRequestException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        }
    }
}



