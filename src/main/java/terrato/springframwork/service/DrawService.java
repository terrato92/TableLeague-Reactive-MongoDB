package terrato.springframwork.service;

import terrato.springframwork.domain.BalanceOfMatches;

/**
 * Created by onenight on 2018-03-04.
 */
public interface DrawService {
    BalanceOfMatches drawMatch(Long idTeam);
}
