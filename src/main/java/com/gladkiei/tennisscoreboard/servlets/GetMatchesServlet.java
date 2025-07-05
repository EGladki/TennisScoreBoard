package com.gladkiei.tennisscoreboard.servlets;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.service.PaginationService;
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
    private final PaginationService paginationService = new PaginationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNumber = req.getParameter("page");
        String playerName = req.getParameter("filter_by_player_name");

        int page = getPageNumber(pageNumber);

        if (playerName == null || playerName.isBlank()) {
            List<Match> matches = matchDao.getFiveMatches(paginationService.calculateStartIdForPagination(page));
            int totalPages = paginationService.getCountOfPages(matchDao.getCountOfMatches());
            req.setAttribute("matches", matches);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", totalPages);

        } else {
            List<Match> matches = matchDao.getFiveMatchesWithCurrentPlayer(playerName, paginationService.calculateStartIdForPagination(page));
            int totalPages = paginationService.getCountOfPages(matchDao.getCountOfMatchesWithCurrentPlayer(playerName));
            req.setAttribute("matches", matches);
            req.setAttribute("playerName", playerName);
            req.setAttribute("page", page);
            req.setAttribute("totalPages", totalPages);
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

}