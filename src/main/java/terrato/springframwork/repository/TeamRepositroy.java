package terrato.springframwork.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframwork.domain.Team;

/**
 * Created by onenight on 2018-03-03.
 */
public interface TeamRepositroy extends CrudRepository<Team, Long> {
}
