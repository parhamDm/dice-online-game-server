package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.ResponseList;
import ir.game.services.GameService;
import ir.game.services.MatchFinderService;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    MatchFinderService matchFinderService;

    @RequestMapping(path = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> create(ServletRequest req, @RequestBody Game game){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean=gameService.create(username,game);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/get",method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam Long gameId){
        Game game =gameService.get(gameId);
        if (game ==null){
            return new ResponseEntity<>("GameNotFound",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Game>(game,HttpStatus.OK);

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
        ResponseList<Game> responseBean=gameService.listOfAllGames();
        return new ResponseEntity<>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/gamesPlayed",method = RequestMethod.GET)
    public ResponseEntity<?> getPlayedGames(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseList<?> responseBean=userService.gamesPlayed(username);
        return new ResponseEntity<ResponseList<?>>(responseBean,HttpStatus.OK);
    }


    @RequestMapping(path = "/requestPlay",method = RequestMethod.GET)
    public ResponseEntity<?> requestPlay(ServletRequest req,@RequestParam Long gameId){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean= matchFinderService.addToQueue(username,gameId);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/requestStatus",method = RequestMethod.GET)
    public ResponseEntity<?> requestStatus(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean= matchFinderService.statusOfRequest(username);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }

}
