package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.reactiveRepository.LeagueReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.PointsService;
import terrato.springframework.service.TeamService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by onenight on 2018-03-04.
 */
public class TeamServiceImplTest {

    @Mock
    TeamReactiveRepository teamRepository;

    @Mock
    LeagueService leagueService;

    @Mock
    LeagueReactiveRepository leagueRepository;

    @Mock
    TeamService teamService;


    @Mock
    BalanceOfMatchesRepository balanceOfMatchesRepository;

    @Mock
    PointsService pointsService;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        teamService = new TeamServiceImpl(leagueRepository, balanceOfMatchesRepository, teamToTeamConvert, balanceOfMatchesDtoToBalanceOfMatches, nationalityDto, teamRepository, pointsService);

    }

    @Test
    public void testFindyLeagueIdAndTeamId() throws Exception {
        League league = getLeague();

        Optional<League> leagueOptional = Optional.of(league);

        when(leagueRepository.findById(anyString())).thenReturn(Mono.just(league));

        Team team = teamService.findByLeagueIdAndTeamId("Seria A", "Napoli").block();

        assertEquals("Napoli", team.getId());

    }

    private League getLeague() {
        League league = new League();
        league.setId("Seria A");

        Team team1 = new Team();
        team1.setId("Napoli");

        Team team2 = new Team();
        team2.setId("Juventus");

        Team team3 = new Team();
        team3.setId("Inter");

        league.addTeam(team1);
        league.addTeam(team2);
        league.addTeam(team3);
        return league;
    }

    @Test
    public void testFindTeamById() throws Exception {

        Team team = new Team();
        team.setId("aa");

        when(teamRepository.findById(anyString())).thenReturn(Mono.just(team));

        Team team1 = teamService.findTeamById("aa").block();

        assertNotNull(team1);
        assertEquals(team.getId(), "aa");

    }
}