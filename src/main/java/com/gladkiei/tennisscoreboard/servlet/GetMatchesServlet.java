package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.models.Match;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class GetMatchesServlet extends HttpServlet {

    private final MatchDao matchDao = new MatchDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNumber = req.getParameter("page_number");
        String playerName = req.getParameter("filter_by_player_name");

        int page = getPageNumber(pageNumber);

        if (playerName == null || playerName.isBlank()) {
            List<Match> matches = matchDao.getFiveMatches(calculateStartIdForPagination(page));
            req.setAttribute("matches", matches);
        } else {
            List<Match> matches = matchDao.getFiveMatchesWithCurrentPlayer(playerName, calculateStartIdForPagination(page));
            req.setAttribute("matches", matches);
        }

        req.getRequestDispatcher("/matches.jsp").forward(req, resp);


    }

    private int getPageNumber(String pageNumber) {
        if (pageNumber == null || pageNumber.isBlank()) {
            return 1;
        } else {
            return Integer.parseInt(pageNumber);
        }
    }

    private int calculateStartIdForPagination(int page) {
        if (page == 1) {
            return page;
        } else {
            return (page - 1) * 5 + 1;
        }
    }

}