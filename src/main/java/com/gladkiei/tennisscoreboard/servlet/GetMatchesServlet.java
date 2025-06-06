package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/matches")
public class GetMatchesServlet extends HttpServlet {

    private final MatchDao matchDao = new MatchDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Match> matches = matchDao.getAll();

        matches.sort(Comparator.comparing(match -> match.getPlayer1().getName()));

        List<Match> sorted = matches.stream().sorted().toList();

        req.setAttribute("matches", matches);
        req.getRequestDispatcher("/matches.jsp").forward(req, resp);
    }

    //matches?page=$page_number&filter_by_player_name=$player_name
}
