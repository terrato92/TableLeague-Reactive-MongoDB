package terrato.springframework.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import terrato.springframework.domain.BalanceOfMatches;
import terrato.springframework.domain.Team;
import terrato.springframework.repository.BalanceOfMatchesRepository;
import terrato.springframework.repository.TeamRepository;
import terrato.springframework.service.DefeatService;
import terrato.springframework.service.DrawService;
import terrato.springframework.service.WinService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by onenight on 2018-03-04.
 */
public class BalanceOfMatchesImplTest {

    @Mock
    TeamRepository teamRepository;

    @Mock
    BalanceOfMatchesRepository balanceOfMatchesRepository;

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
        team.setId(1L);
        team.setBalanceOfMatches(new BalanceOfMatches());

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        winService.winMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getWins());
    }

    @Test
    public void drawMatch() throws Exception {
        BalanceOfMatches balanceOfMatches = new BalanceOfMatches();
        balanceOfMatches.setBalance_id(1L);
        Team team = new Team();
        team.setId(1L);
        team.setBalanceOfMatches(balanceOfMatches);

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        drawService.drawMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getDraws());
    }

    @Test
    public void defeatMatch() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setBalanceOfMatches(new BalanceOfMatches());

        when(teamRepository.findOne(anyLong())).thenReturn(team);

        defeatService.defeatMatch(team.getId());

        assertEquals(1, team.getBalanceOfMatches().getDefeats());

    }

}