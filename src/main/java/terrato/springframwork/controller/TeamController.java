package terrato.springframwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import terrato.springframwork.domain.Team;
import terrato.springframwork.service.TeamService;

/**
 * Created by onenight on 2018-03-04.
 */
@Controller
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping("team/new")
    public String newTeam(Model model){
        model.addAttribute("team", new Team());

        return "team/list";
    }

    @GetMapping
    @RequestMapping("team/list")
    public String getTeams(Model model){
        model.addAttribute("teams", teamService.getTeams());

        return "team/list";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/teams")
    public String getTeamsFromLeagues(@PathVariable String leagueId, Model model){
        model.addAttribute("teams", teamService.getTeamsFromLeague(Long.valueOf(leagueId)));

        return "league/teams";
    }

    @GetMapping
    @RequestMapping("team/{id}")
    public String findTeamById(@PathVariable String leagueId, Model model){
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(leagueId)));

        return "team/"+ leagueId + "/show";
    }

    @RequestMapping("team/{id}/update")
    public String saveOrUpdateTeam(@PathVariable String id, @ModelAttribute Team team){
        Team updateTeam = teamService.updateTeam(team, Long.valueOf(id));

        return "team/" + updateTeam.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}")
    public String addTeamToLeague(@PathVariable String leagueId,
                                  @PathVariable String teamId, Model model){
        model.addAttribute("team", teamService.addTeamToLeague(Long.valueOf(leagueId), Long.valueOf(teamId)));

        return "team/" + teamId +  "/show";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/delete")
    public String deleteTeamFromLeague(@PathVariable String leagueId,
                                       @PathVariable String teamId){
        teamService.deleteTeamFromLeague(Long.valueOf(leagueId), Long.valueOf(teamId));

        return "league/" + leagueId + "teams/list";
    }

    @RequestMapping("team/{id}/delete")
    public String deleteById(@PathVariable String id){
        teamService.deleteTeam(Long.valueOf(id));

        return "teams/list";
    }

    @RequestMapping("league/{id}/sort")
    public String sortLeague(@PathVariable String id, Model model){
        model.addAttribute("league_list", teamService.setTeamByPoints(Long.valueOf(id)));

        return "league/show";
    }

}
