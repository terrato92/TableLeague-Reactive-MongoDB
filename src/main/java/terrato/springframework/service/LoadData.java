package terrato.springframework.service;

import terrato.springframework.domain.Team;

import java.util.Set;

/**
 * Created by onenight on 2018-03-19.
 */
public interface LoadData {
    Set<Team> loadLeagues();
}
