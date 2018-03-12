package terrato.springframework.repository;

import org.springframework.data.repository.CrudRepository;
import terrato.springframework.domain.Nationality;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-05.
 */
public interface NationalityRepository extends CrudRepository<Nationality, Long> {

    Optional<Nationality> findByState(String state);
}
