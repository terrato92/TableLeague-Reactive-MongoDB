package terrato.springframwork.controller;

import org.springframework.stereotype.Controller;
import terrato.springframwork.service.PlayerService;

/**
 * Created by onenight on 2018-03-05.
 */
@Controller
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

}
