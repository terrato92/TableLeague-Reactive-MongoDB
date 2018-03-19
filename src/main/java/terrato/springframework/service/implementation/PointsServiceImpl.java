package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import terrato.springframework.domain.Team;
import terrato.springframework.service.PointsService;

/**
 * Created by onenight on 2018-03-16.
 */
@Service
public class PointsServiceImpl implements PointsService {

    @Override
    public int conversPoints(Team team) {

            int wins = team.getBalanceOfMatches().getWins() * 3;
            int draw = team.getBalanceOfMatches().getDraws();
            int defeat = 0;

            team.setPoints(wins + draw);

            return  wins + draw;
        }
    }

