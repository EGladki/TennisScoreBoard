package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.OngoingMatch;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.service.OngoingMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/update-score")
public class UpdateScoreServlet extends HttpServlet {

    private final PlayerDao playerDao = new PlayerDao();
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


    }
}

