package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchDao;

public class PaginationService {
    private static final int RESULTS_ON_PAGE = 5;
    private final MatchDao matchDao = new MatchDao();

    public int getCountOfPages(int countOfMatches) {
        if (countOfMatches <= RESULTS_ON_PAGE) {
            return 1;
        } else {
            if (countOfMatches % RESULTS_ON_PAGE == 0) {
                return countOfMatches / RESULTS_ON_PAGE;
            } else {
                return countOfMatches / RESULTS_ON_PAGE + 1;
            }
        }
    }

    public int calculateStartIdForPagination(int page) {
        if (page == 1) {
            return page;
        } else {
            return (page - 1) * RESULTS_ON_PAGE + 1;
        }
    }


}
