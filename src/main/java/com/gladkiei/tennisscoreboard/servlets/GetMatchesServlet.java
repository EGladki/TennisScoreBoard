package com.gladkiei.tennisscoreboard.servlets;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.exceptions.BadRequestException;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.service.PaginationService;
import com.gladkiei.tennisscoreboard.utils.NameValidationUtils;
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
    private final NameValidationUtils nameValidationUtils = new NameValidationUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pageNumber = req.getParameter("page");
            String playerName = req.getParameter("filter_by_player_name");
            int page = parsePageNumber(pageNumber);

            if (playerName == null || playerName.isBlank()) {
                List<Match> matches = matchDao.getFiveMatches(paginationService.calculateStartIdForPagination(page));
                int totalPages = paginationService.getCountOfPages(matchDao.getCountOfMatches());
                req.setAttribute("matches", matches);
                req.setAttribute("page", page);
                req.setAttribute("totalPages", totalPages);

            } else {
                playerName = playerName.trim();
                nameValidationUtils.validate(playerName);
                List<Match> matches = matchDao.getFiveMatchesWithCurrentPlayer(playerName.toUpperCase(), paginationService.calculateStartIdForPagination(page));
                int totalPages = paginationService.getCountOfPages(matchDao.getCountOfMatchesWithCurrentPlayer(playerName));
                req.setAttribute("matches", matches);
                req.setAttribute("playerName", playerName);
                req.setAttribute("page", page);
                req.setAttribute("totalPages", totalPages);
            }

        } catch (BadRequestException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("matches.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("matches.jsp").forward(req, resp);
    }

    private int parsePageNumber(String pageNumber) {
        if (pageNumber == null || pageNumber.isBlank()) {
            return 1;
        } else {
            return Integer.parseInt(pageNumber);
        }
    }

}