package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import terrato.springframwork.domain.Player;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.PlayerRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.PlayerService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    PlayerRepository playerRepository;
    TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Player createPlayer(Player playerSource) {
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findOne(playerSource.getId()));

        if (!playerOptional.isPresent()) {
            Player player = new Player();

            player.setName(playerSource.getName());
            player.setAge(playerSource.getAge());
            player.setTeam(playerSource.getTeam());

            player.setPosition(playerSource.getPosition());

            playerRepository.save(player);

            return player;
        } else {
            log.error("Player with id: " + playerSource.getId() + "already exist.");
            throw new RuntimeException("You can't add player with the same id");
        }
    }

    @Override
    public Collection<Player> getPlayers() {
        Set<Player> playerSet = new HashSet<>();
        playerRepository.findAll().iterator().forEachRemaining(playerSet::add);
        return playerSet;
    }

    @Override
    public Collection<Player> getPlayersFromTeam(Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));

        if (teamOptional.isPresent()) {
            return teamOptional.get().getPlayers();
        } else {
            log.error("Team with id: " + idTeam + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }
    }

    @Override
    public Collection<Player> addPlayerToTeam(Player player, Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            player.setTeam(team);
            team.getPlayers().add(player);

            playerRepository.save(player);
            teamRepository.save(team);

            return team.getPlayers();
        } else {
            log.error("Team with id: " + idTeam + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }
    }

    @Override
    public void deletePlayer(Long idPlayer) {
        Optional<Player> playerOptional = Optional.ofNullable(playerRepository.findOne(idPlayer));

        if (playerOptional.isPresent()) {
            playerRepository.delete(idPlayer);
        }
    }

    @Override
    public Collection<Player> deletePlayerFromTeam(Long idPlayer, Long idTeam) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idPlayer));

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            Optional<Player> playerOptional = team.getPlayers().stream()
                    .filter(player -> player.getId().equals(idPlayer))
                    .findFirst();

            Player playerToDelete = playerOptional.get();

            team.getPlayers().remove(playerToDelete);

            teamRepository.save(team);

            return team.getPlayers();
        } else {
            log.error("Team with id: " + idTeam + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }
    }
}