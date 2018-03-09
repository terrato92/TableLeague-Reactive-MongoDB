package terrato.springframwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import terrato.springframwork.domain.League;
import terrato.springframwork.domain.Nationality;
import terrato.springframwork.domain.Team;
import terrato.springframwork.service.LeagueService;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.TeamService;

import javax.validation.Valid;

/**
 * Created by onenight on 2018-03-04.
 */
@Controller
public class TeamController {

    private final TeamService teamService;
    private final LeagueService leagueService;
    private final NationalityService nationalityService;

    @Autowired
    public TeamController(TeamService teamService, LeagueService leagueService, NationalityService nationalityService) {
        this.teamService = teamService;
        this.leagueService = leagueService;
        this.nationalityService = nationalityService;
    }

    @PostMapping
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

        model.addAttribute("leagues", leagueService.showLeagueTeams(Long.valueOf(leagueId)));

        model.addAttribute("teams", leagueService.showLeagueTeams(Long.valueOf(leagueId)));

        return "league/team/list";
    }

    @GetMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/show")
    public String showLeagueTeamById (@PathVariable String leagueId,
                                      @PathVariable String teamId, Model model) {
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(leagueId), Long.valueOf(teamId)));

        return "league/team/show";
    }


    @PostMapping
    @RequestMapping("league/{leagueId}/team/{teamId}/update")
    public String updateLeagueTeam(@PathVariable String leagueId,
                                   @PathVariable String teamId, Model model){
        model.addAttribute("team", teamService.findTeamById(Long.valueOf(leagueId), Long.valueOf(teamId)));

        model.addAttribute("states", nationalityService.listAllStates());

        return "league/team/teamform";
    }

    @PostMapping
    @RequestMapping("league/{leagueId}/team")
    public String saveOrUpdateTeam(@Valid @ModelAttribute("team") Team team){

        Team updateTeam = teamService.findTeamById(team.getLeague().getId(), team.getLeague().getId());

        return "redirect:/league/" + updateTeam.getLeague().getId() + "/team/" + updateTeam.getId()+"/show";
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

        return "redirect:/league/" + leagueId + "/teams";
    }

    @RequestMapping("team/{id}/remove")
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
