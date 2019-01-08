package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    GameService gameService;

    @RequestMapping(path = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(ServletRequest req, @RequestBody Game game){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean=gameService.create(username,game);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }
}
