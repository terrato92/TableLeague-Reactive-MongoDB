package terrato.springframwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Nationality;
import terrato.springframwork.domain.Team;
import terrato.springframwork.service.LeagueService;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.TeamService;

/**
 * Created by onenight on 2018-03-04.
 */
@Controller
public class TeamController {

    private final TeamService teamService;
    private final LeagueService leagueService;
    private final NationalityService nationalityService;

    public TeamController(TeamService teamService, LeagueService leagueService, NationalityService nationalityService) {
        this.teamService = teamService;
        this.leagueService = leagueService;
        this.nationalityService = nationalityService;
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/new")
    public String newTeam(@PathVariable String leagueId, Model model){
        League league = leagueService.getLeagueById(Long.valueOf(leagueId));

        Team team = new Team();
        team.setLeague(league);
        model.addAttribute("team", team);

        team.setState(new Nationality());
        model.addAttribute("nationalList", nationalityService.listAllStates());

        return "league/team/teamform";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/teams")
    public String getTeams(@PathVariable String leagueId, Model model){

        model.addAttribute("teams", leagueService.getLeagueById(Long.valueOf(leagueId)));

        return "league/team/list";
    }

    @RequestMapping("league/{leagueId}/team/{teamId}/show")
    public String showLeagueTeamById (@PathVariable String leagueId,
                                      @PathVariable String teamId, Model model) {
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(leagueId), Long.valueOf(teamId)));

        return "league/team/show";
    }


    @RequestMapping("league/{leagueId}/team/{teamId}/update")
    public String updateLeagueTeam(@PathVariable String leagueId,
                                   @PathVariable String teamId, Model model){
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(leagueId), Long.valueOf(teamId)));

        model.addAttribute("states", nationalityService.listAllStates());

        return "league/team/teamform";
    }

    @RequestMapping("league/{leagueId}/team")
    public String saveOrUpdateTeam(@ModelAttribute Team team){
        Team updateTeam = teamService.saveTeam(team);

        return "redirect:/league/" + updateTeam.getId() + "/team/" + team.getId();
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}")
    public String addTeamToLeague(@PathVariable String leagueId,
                                  @PathVariable String teamId, Model model){
        model.addAttribute("team", teamService.addTeamToLeague(Long.valueOf(leagueId), Long.valueOf(teamId)));

        return "team/" + teamId + "/show";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/delete")
    public String deleteLeagueTeam(@PathVariable String leagueId,
                                       @PathVariable String teamId){
        teamService.deleteTeamFromLeague(Long.valueOf(leagueId), Long.valueOf(teamId));

        return "redirect:/league/" + leagueId + "teams";
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
