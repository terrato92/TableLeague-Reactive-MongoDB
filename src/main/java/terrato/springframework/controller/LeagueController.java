package terrato.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import terrato.springframework.domain.League;
import terrato.springframework.dto.LeagueDto;
import terrato.springframework.dto.NationalityDto;
import terrato.springframework.service.LeagueService;
import terrato.springframework.service.NationalityService;
import terrato.springframework.service.TeamService;

/**
 * Created by onenight on 2018-03-03.
 */
@Controller
public class LeagueController {

    private final LeagueService leagueService;

    private final TeamService teamService;

    private final NationalityService nationalityService;

    private WebDataBinder webDataBinder;

    public LeagueController(LeagueService leagueService, TeamService teamService, NationalityService nationalityService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.nationalityService = nationalityService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        this.webDataBinder = webDataBinder;
    }

    @RequestMapping({"", "/", "/index"})
    public String showLeagues(Model model){

        model.addAttribute("leagues", leagueService.getLeagues().collectList().block());

        return "index";
    }

    @GetMapping("league/{id}/show")
    public String showLeagueById(@PathVariable String id, Model model) {
        model.addAttribute("league", leagueService.getLeagueById(id).block());

        model.addAttribute("teams", leagueService.showLeagueTeams(id).block());

        return "league/show";
    }


    @GetMapping("league/new")
    public String newLeague(Model model) {

        model.addAttribute("league", new League());

        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

        return "league/leagueform";
    }

    @GetMapping("league/{id}/delete")
    public String deleteLeague(@PathVariable String id){
        leagueService.deleteLeagueById(id);
        return "redirect:/";
    }

    @GetMapping("league/{leagueId}/update")
    public String updateLeague(@PathVariable String leagueId, Model model){
        model.addAttribute("league", leagueService.getLeagueDtoById(leagueId).block());

//        model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

        return "league/leagueform";
    }

    @PostMapping("league")
    public String saveOrUpdate(@ModelAttribute("league") LeagueDto league, Model model){

        webDataBinder.validate();

        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> objectError.toString());

//            model.addAttribute("nationalities", nationalityService.listAllNationalities().collectList().block());

            return "league/leagueform";
        }

        LeagueDto league1 = leagueService.saveLeagueDto(league).block();


        return "redirect:/league/" + league1.getId() + "/show";
    }

    @ModelAttribute("nationalities")
    public Flux<NationalityDto> populateNationalityList() {
        return nationalityService.listAllNationalities();
    }
}
