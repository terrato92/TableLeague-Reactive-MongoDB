package terrato.springframwork.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import terrato.springframwork.domain.Player;
import terrato.springframwork.domain.Team;
import terrato.springframwork.repository.NationalityRepository;
import terrato.springframwork.repository.PlayerRepository;
import terrato.springframwork.repository.TeamRepository;
import terrato.springframwork.service.PlayerService;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final NationalityRepository nationalityRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, NationalityRepository nationalityRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;

        this.nationalityRepository = nationalityRepository;
    }


    @Override
    public Collection<Player> getPlayersFromTeam(Long idTeam) {
        Optional<Set<Player>> playerOptional = playerRepository.getByTeamId(idTeam);

        if (playerOptional.isPresent()) {
            return playerOptional.get();
        } else {
            log.error("Team with id: " + idTeam + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }
    }

    @Override
    public Player getTeamPlayerById(Long idTeam, Long idPlayer) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(idTeam));

        if (!teamOptional.isPresent()) {
            throw new RuntimeException("Team doesn't exist");
        } else {
            Team team = teamOptional.get();

            Optional<Player> playerOptional = team.getPlayers().stream().filter(player -> player.getId().equals(idPlayer)).findFirst();

            if (!playerOptional.isPresent()) {
                throw new RuntimeException("Player doesn't exist");
            } else {
                return playerOptional.get();
            }
        }
    }

    @Override
    @Transactional
    public Player savePlayer(Player player) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOne(player.getTeam().getId()));

        if (!teamOptional.isPresent()) {
            throw new RuntimeException("Team doesn't exist");
        } else {
            Team team = teamOptional.get();

            Optional<Player> playerOptional = team
                    .getPlayers()
                    .stream()
                    .filter(player3 -> player3.getId().equals(player.getId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                Player player1 = playerOptional.get();
                player1.setTeam(player.getTeam());
                player1.setName(player.getName());

                player1.setState(nationalityRepository.findOne(player.getState().getId()));

                player1.setAge(player.getAge());
                player1.setPosition(player.getPosition());
                return player1;

            } else {
                team.addPlayer(player);
                player.setTeam(team);

                return player;
            }
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
            playerToDelete.setTeam(null);
            team.getPlayers().remove(playerToDelete);

            teamRepository.save(team);

            return team.getPlayers();
        } else {
            log.error("Team with id: " + idTeam + " doesn't exist");
            throw new RuntimeException("I can't find team with id: " + idTeam);
        }
    }
}