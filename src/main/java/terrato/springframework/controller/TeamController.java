package terrato.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import terrato.springframework.converter.TeamDtoToTeam;
import terrato.springframework.converter.TeamToTeamDtoConvert;
import terrato.springframework.domain.Nationality;
import terrato.springframework.domain.Team;
import terrato.springframework.dto.LeagueDto;
import terrato.springframework.dto.TeamDto;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.PlayerService;
import terrato.springframework.service.TeamService;

/**
 * Created by onenight on 2018-03-04.
 */
@Controller
public class TeamController {

    private final TeamService teamService;
    private final LeagueService leagueService;
    private final NationalityService nationalityService;
    private final PlayerService playerService;
    private final TeamToTeamDtoConvert teamToTeamDtoConvert;
    private final TeamDtoToTeam teamDtoToTeam;

    private WebDataBinder webDataBinder;

    public TeamController(TeamService teamService, LeagueService leagueService, NationalityService nationalityService, PlayerService playerService, TeamToTeamDtoConvert teamToTeamDtoConvert, TeamDtoToTeam teamDtoToTeam) {
        this.teamService = teamService;
        this.leagueService = leagueService;
        this.nationalityService = nationalityService;
        this.playerService = playerService;
        this.teamToTeamDtoConvert = teamToTeamDtoConvert;
        this.teamDtoToTeam = teamDtoToTeam;
    }

    @InitBinder("team")
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("league/{leagueId}/team/new")
    public String newTeam(@PathVariable String leagueId, Model model) {

        LeagueDto league = leagueService.getLeagueDtoById(leagueId).block();

        Team team = new Team();
        TeamDto teamDto = teamToTeamDtoConvert.convert(team);
        teamDto.setLeagueId(league.getId());

        model.addAttribute("team", teamDto);

        team.setNationality(new Nationality());
        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

        return "team/teamform";
    }

    @GetMapping("league/{leagueId}/teams")
    public String getTeams(@PathVariable String leagueId, Model model) {

        model.addAttribute("teams", leagueService.showLeagueTeams(leagueId).block());

        return "league/team/list";
    }

    @GetMapping("league/{leagueId}/team/show")
    public String showLeagueTeamById(@PathVariable String leagueId,
                                     Model model) {
        model.addAttribute("team", leagueService.showLeagueTeams(leagueId).block());

//        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());


        return "team/list";
    }


    @GetMapping("team/{teamId}/update")
    public String updateLeagueTeam(@PathVariable String teamId, Model model) {
        model.addAttribute("team", teamService.findTeamById(teamId).block());

        return "team/teamform";
    }


    @PostMapping("league/{leagueId}/team")
    public String saveOrUpdateTeam(@PathVariable String leagueId, @ModelAttribute("team") TeamDto teamDto) {

        webDataBinder.validate();

        BindingResult bindingResult = webDataBinder.getBindingResult();


        if (bindingResult.hasErrors()) {
            teamDto.setLeagueId(leagueId);

            bindingResult.getAllErrors().forEach(ObjectError::toString);
            return "team/teamform";
        }

        teamDto.setLeagueId(leagueId);

        TeamDto team = teamService.saveTeam(teamDto).block();
        team.setLeagueId(leagueId);

        return "redirect:/league/" +  team.getLeagueId() +  "/team/show";
    }

    @PostMapping("team/{id}/delete")
    public String deleteById(@PathVariable String id) {
        Long idLeague = Long.valueOf(id);
        teamService.deleteTeam(id);

        return "redirect:/league/" + idLeague + "/show";
    }
}
