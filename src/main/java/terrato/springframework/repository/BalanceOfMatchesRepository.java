package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframework.domain.BalanceOfMatches;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-04.
 */
public interface BalanceOfMatchesRepository extends CrudRepository<BalanceOfMatches, Long> {

    Optional<BalanceOfMatches> findByWins(int wins);
}
