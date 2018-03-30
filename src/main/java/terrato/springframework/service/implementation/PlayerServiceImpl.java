package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.Player;
import terrato.springframework.domain.Team;
import terrato.springframework.exception.NotFoundException;
import terrato.springframework.repository.reactiveRepository.NationalityReactiveRepository;
import terrato.springframework.repository.reactiveRepository.PlayerReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.PlayerService;

import java.util.Optional;
import java.util.Set;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerReactiveRepository playerRepository;
    private final TeamReactiveRepository teamRepository;
    private final NationalityReactiveRepository nationalityRepository;

    public PlayerServiceImpl(PlayerReactiveRepository playerRepository,
                             TeamReactiveRepository teamRepository,
                             NationalityReactiveRepository nationalityRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;

        this.nationalityRepository = nationalityRepository;
    }


    @Override
    public Mono<Set<Player>> getPlayersFromTeam(String idTeam) {
        Mono<Set<Player>> player = playerRepository.findByTeamId(idTeam);

        if (player != null) {
            return player;
        } else {
            throw new NotFoundException("I can't find team with id: " + idTeam);
        }
    }

    @Override
    public Mono<Player> getTeamPlayerById(String idPlayer) {
        Player playerOptional = playerRepository.findById(idPlayer).block();

        if (playerOptional == null) {
            throw new NotFoundException("Player doesn't exist");
        } else {
            return Mono.just(playerOptional);
        }
    }

    @Override
    public Mono<Player> savePlayer(Player player, String teamId) {
        Team team = teamRepository.findById(teamId).block();

        if (team == null) {
            throw new NotFoundException("Team doesn't exist");
        } else {

            Optional<Player> playerOptional = team
                    .getPlayers()
                    .stream()
                    .filter(player3 -> player3.getId().equals(player.getId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                Player player1 = playerOptional.get();
                player1.setTeam(team);
                player1.setName(player.getName());

                player1.setNationality(nationalityRepository.findById(player.getNationality().getId()).block());

                player1.setAge(player.getAge());
                player1.setPosition(player.getPosition());
                return Mono.just(player1);

            } else {
                team.addPlayer(player);
                player.setTeam(team);

                return Mono.just(player);
            }
        }
    }


    @Override
    public Mono<Void> deletePlayerFromTeam(String idPlayer) {
            playerRepository.deleteById(idPlayer);
            return Mono.empty();
        }
    }
