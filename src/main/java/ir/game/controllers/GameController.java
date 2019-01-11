package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;
import ir.game.models.GameSession;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.ResponseList;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.services.GameService;
import ir.game.services.UserService;
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

    @Autowired
    UserService userService;

    @RequestMapping(path = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(ServletRequest req, @RequestBody Game game){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean=gameService.create(username,game);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/ownGames",method = RequestMethod.GET)
    public ResponseEntity<?> ownGames(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseList<Game> responseBean=gameService.listOfOwnGames(username);
        return new ResponseEntity<ResponseList<Game>>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/AllGames",method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseList<Game> responseBean=gameService.listOfOwnGames(username);
        return new ResponseEntity<ResponseList<Game>>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/gamesPlayed",method = RequestMethod.GET)
    public ResponseEntity<?> getPlayedGames(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseList<?> responseBean=userService.gamesPlayed(username);
        return new ResponseEntity<ResponseList<?>>(responseBean,HttpStatus.OK);
    }


}
