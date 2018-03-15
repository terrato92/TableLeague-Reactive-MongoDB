package terrato.springframework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import terrato.springframework.domain.League;
import terrato.springframework.domain.Nationality;
import terrato.springframework.domain.Team;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

import javax.validation.Valid;

/**
 * Created by onenight on 2018-03-04.
 */
@Slf4j
@Controller
public class TeamController {

    private final TeamService teamService;
    private final LeagueService leagueService;
    private final NationalityService nationalityService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    public TeamController(TeamService teamService, LeagueService leagueService, NationalityService nationalityService) {
        this.teamService = teamService;
        this.leagueService = leagueService;
        this.nationalityService = nationalityService;
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/new")
    public String newTeam(@PathVariable String leagueId, Model model) {
        League league = new League();
        league.setId(Long.valueOf(leagueId));

        Team team = new Team();
        team.setLeague(league);
        model.addAttribute("team", team);

        team.setNationality(new Nationality());
        model.addAttribute("nationalities", nationalityService.listAllNationalities());

        return "team/teamform";
    }

    @GetMapping
    @RequestMapping("team/{teamId}/show")
    public String getTeamById(@PathVariable String teamId, Model model){
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(teamId)));

        model.addAttribute("players", playerService.getPlayersFromTeam(Long.valueOf(teamId)));

        return "team/show";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/teams")
    public String getTeams(@PathVariable String leagueId, Model model) {


        model.addAttribute("teams", leagueService.showLeagueTeams(Long.valueOf(leagueId)));

        return "league/team/list";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/show")
    public String showLeagueTeamById(@PathVariable String leagueId,
                                     Model model) {
        model.addAttribute("team", teamService.findTeamByLeagueId(Long.valueOf(leagueId)));

        model.addAttribute("nationalities", nationalityService.listAllNationalities());


        return "league/team/list";
    }


    @GetMapping
    @RequestMapping("team/{teamId}/update")
    public String updateLeagueTeam(@PathVariable Long teamId, Model model) {
        model.addAttribute("team", teamService.findTeamById(teamId));


        return "team/teamform";
    }

    @PostMapping
    @RequestMapping("league/{leagueId}/team")
    public String saveOrUpdateTeam(@PathVariable Long leagueId, @Valid @ModelAttribute ("team") Team team,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            League league = new League();
            league.setId(leagueId);

            team.setLeague(league);

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "team/teamform";
        }

        Team updateTeam = teamService.saveTeam(team, leagueId);

        return "redirect:/league/" + leagueId + "/show";
    }

    @RequestMapping("team/{id}/delete")
    public String deleteById(@PathVariable String id) {
        Team team = teamService.findTeamById(Long.valueOf(id));

        Long idLeague = team.getLeague().getId();

        teamService.deleteTeam(Long.valueOf(id));

        return "redirect:/league/" + idLeague + "/show";
    }

    @RequestMapping("league/{id}/sort")
    public String sortLeague(@PathVariable String id, Model model) {
        model.addAttribute("league_list", teamService.setTeamByPoints(Long.valueOf(id)));

        return "league/show";
    }

}
