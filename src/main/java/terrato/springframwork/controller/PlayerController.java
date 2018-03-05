package terrato.springframwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import terrato.springframwork.service.NationalityService;
import terrato.springframwork.service.PlayerService;
import terrato.springframwork.service.TeamService;

/**
 * Created by onenight on 2018-03-05.
 */
@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final NationalityService nationalityService;

    public PlayerController(PlayerService playerService, TeamService teamService, NationalityService nationalityService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.nationalityService = nationalityService;
    }

    @RequestMapping("team/{teamId}/show")
    public String showTeamPlayersById(@PathVariable String teamId, Model model){
        model.addAttribute("teams", playerService.getPlayersFromTeam(Long.valueOf(teamId)));

        return "team/players/show";
    }

}
