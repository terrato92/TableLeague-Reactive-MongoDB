package terrato.springframework.service.implementation;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import terrato.springframework.converter.BalanceOfMatchesDtoToBalanceOfMatches;
import terrato.springframework.converter.NationalityDtoToNationality;
import terrato.springframework.converter.TeamDtoToTeam;
import terrato.springframework.converter.TeamToTeamDtoConvert;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.TeamDto;
import terrato.springframework.exception.NotFoundException;
import terrato.springframework.repository.reactiveRepository.LeagueReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.PointsService;
import terrato.springframework.service.TeamService;

import java.util.Optional;

/**
 * Created by onenight on 2018-03-03.
 */
@Service
public class TeamServiceImpl implements TeamService {

    private  final LeagueReactiveRepository leagueRepository;
    private final TeamToTeamDtoConvert teamToTeamDtoConvert;
    private final TeamDtoToTeam teamDtoToTeam;
    private final BalanceOfMatchesDtoToBalanceOfMatches balanceOfMatchesDtoToBalanceOfMatches;
    private final NationalityDtoToNationality nationalityDtoToNationality;
    private final TeamReactiveRepository teamRepository;

    private final PointsService pointsService;

    public TeamServiceImpl(LeagueReactiveRepository leagueRepository,
                           TeamToTeamDtoConvert teamToTeamDtoConvert,
                           TeamDtoToTeam teamDtoToTeam,
                           BalanceOfMatchesDtoToBalanceOfMatches balanceOfMatchesDtoToBalanceOfMatches,
                           NationalityDtoToNationality nationalityDtoToNationality,
                           TeamReactiveRepository teamRepository,
                           PointsService pointsService) {

        this.leagueRepository = leagueRepository;
        this.teamToTeamDtoConvert = teamToTeamDtoConvert;
        this.teamDtoToTeam = teamDtoToTeam;
        this.balanceOfMatchesDtoToBalanceOfMatches = balanceOfMatchesDtoToBalanceOfMatches;
        this.nationalityDtoToNationality = nationalityDtoToNationality;
        this.teamRepository = teamRepository;
        this.pointsService = pointsService;
    }


    @Override
    public Mono<TeamDto> saveTeam(TeamDto sourceTeam) {
        League league = leagueRepository.findById(sourceTeam.getLeagueId()).block();

        if (league == null) {
            return Mono.just(new TeamDto());
        } else {

            Optional<Team> teamOptional = league
                    .getTeams()
                    .stream()
                    .filter(team -> team.getId().equals(sourceTeam.getId()))
                    .findFirst();

            if (teamOptional.isPresent()) {

                Team foundTeam = teamOptional.get();

                foundTeam.setName(sourceTeam.getTeam());
                foundTeam.setBalanceOfMatches(balanceOfMatchesDtoToBalanceOfMatches.convert(sourceTeam.getBalanceOfMatches()));
                foundTeam.setPoints(sourceTeam.getPoints());
                foundTeam.setPower(sourceTeam.getPower());
                foundTeam.setNationality(nationalityDtoToNationality.convert(sourceTeam.getNationality()));

//                return Mono.just(teamOptional.get());

            } else {

                Team team = teamDtoToTeam.convert(sourceTeam);
                league.addTeam(team);

            }

            League savedLeague = leagueRepository.save(league).block();

            Optional<Team> savedTeamOptional = savedLeague.getTeams()
                    .stream().filter(team -> team.getId().equalsIgnoreCase(sourceTeam.getId()))
                    .findFirst();

            TeamDto teamDtoSaved = teamToTeamDtoConvert.convert(savedTeamOptional.get());
            teamDtoSaved.setLeagueId(league.getId());

            return Mono.just(teamDtoSaved);
        }
    }

    @Override
    public Mono<Team> findTeamById(String idTeam) {
        return teamRepository.findById(idTeam);
    }

    @Override
    public Mono<TeamDto> findTeamDtoByLeagueIdAndTeamId(String idLeague, String idTeam) {

        return leagueRepository
                .findById(idLeague)
                .flatMapIterable(league -> league.getTeams())
                .filter(team -> team.getId().equalsIgnoreCase(idTeam))
                .single()
                .map(team -> {
                    TeamDto teamDto = teamToTeamDtoConvert.convert(team);
                    teamDto.setLeagueId(idLeague);

                    return teamDto;
                });
    }

    @Override
    public Mono<Void> deleteTeam(String id) {
        Team teamToDelete = teamRepository.findById(id).block();

        if (teamToDelete == null) {
            throw new NotFoundException();
        } else {
            teamRepository.delete(teamToDelete);
            return Mono.empty();
        }
    }
}
