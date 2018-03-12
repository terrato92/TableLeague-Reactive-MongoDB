package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import terrato.springframework.domain.League;

/**
 * Created by onenight on 2018-03-03.
 */
@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {

}
