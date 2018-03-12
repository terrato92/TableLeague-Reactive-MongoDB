package terrato.springframework.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import terrato.springframework.service.DefeatService;
import terrato.springframework.service.DrawService;
import terrato.springframework.service.TeamService;
import terrato.springframework.service.WinService;

/**
 * Created by onenight on 2018-03-07.
 */
public class BalanceOFMatchesController {

    private final TeamService teamService;
    private final WinService winService;
    private final DrawService drawService;
    private final DefeatService defeatService;

    public BalanceOFMatchesController(TeamService teamService, WinService winService, DrawService drawService, DefeatService defeatService) {
        this.teamService = teamService;
        this.winService = winService;
        this.drawService = drawService;
        this.defeatService = defeatService;
    }

    @PostMapping
    @RequestMapping("team/{teamId}/win")
    public String winMatch(@PathVariable String teamId, Model model){
        model.addAttribute("win", winService.winMatch(Long.valueOf(teamId)));

        return "team/show";
    }

    @PostMapping
    @RequestMapping("team/{teamId}/draw")
    public String drawMatch(@PathVariable String teamId, Model model){
        model.addAttribute("win", drawService.drawMatch(Long.valueOf(teamId)));

        return "team/show";
    }

    @PostMapping
    @RequestMapping("team/{teamId}/defeat")
    public String defeatMatch(@PathVariable String teamId, Model model){
        model.addAttribute("win", defeatService.defeatMatch(Long.valueOf(teamId)));

        return "team/show";
    }
}
