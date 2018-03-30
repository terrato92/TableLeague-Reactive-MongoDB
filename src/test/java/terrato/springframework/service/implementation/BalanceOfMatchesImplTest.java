package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.reactiveRepository.BalanceOfMatchesReactiveRepository;
import terrato.springframework.repository.reactiveRepository.TeamReactiveRepository;
import terrato.springframework.service.DefeatService;
import terrato.springframework.service.DrawService;
import terrato.springframework.service.WinService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by onenight on 2018-03-04.
 */
public class BalanceOfMatchesImplTest {

    private static final String ID = "lech";

    @Mock
    TeamReactiveRepository teamRepository;

    @Mock
    BalanceOfMatchesReactiveRepository balanceOfMatchesRepository;

    DefeatService defeatService;
    WinService winService;
    DrawService drawService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        defeatService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
        winService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
        drawService = new BalanceOfMatchesImpl(teamRepository, balanceOfMatchesRepository);
    }

    @Test
    public void winMatch() throws Exception {
        Team team = new Team();
        team.setId(ID);

        when(teamRepository.findById(anyString())).thenReturn(Mono.just(team));

        winService.winMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getWins());
    }

    @Test
    public void drawMatch() throws Exception {

        Team team = new Team();
        team.setId(ID);

        when(teamRepository.findById(anyString())).thenReturn(Mono.just(team));

        drawService.drawMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getDraws());
    }

    @Test
    public void defeatMatch() throws Exception {
        Team team = new Team();
        team.setId(ID);

        when(teamRepository.findById(anyString())).thenReturn(Mono.just(team));

        defeatService.defeatMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getDefeats());

    }

}