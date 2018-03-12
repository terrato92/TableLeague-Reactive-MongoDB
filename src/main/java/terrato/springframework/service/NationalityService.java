package terrato.springframework.service;

import terrato.springframework.domain.Nationality;

import java.util.Set;

/**
 * Created by onenight on 2018-03-05.
 */
public interface NationalityService {

    Set<Nationality> listAllNationalities();
}
