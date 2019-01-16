package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.beans.DiceRoleRequest;
import ir.game.models.beans.GameStatusResponse;
import ir.game.services.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/gameSession")
public class GameSessionController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GameSessionService gameSessionService;

    @RequestMapping(path = "/dice",method = RequestMethod.POST)
    public ResponseEntity<?> create(ServletRequest req, @RequestBody DiceRoleRequest gameToken){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        try{
            GameStatusResponse diceRoleRequest =gameSessionService.dice(username,gameToken.getGameToken());
            return new ResponseEntity<>(diceRoleRequest, HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>("unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(path = "/hold",method = RequestMethod.POST)
    public ResponseEntity<?> hold(ServletRequest req, @RequestBody DiceRoleRequest gameToken){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        try{
            GameStatusResponse diceRoleRequest =gameSessionService.hold(username,gameToken.getGameToken());
            return new ResponseEntity<>(diceRoleRequest, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>("unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "/status",method = RequestMethod.POST)
    public ResponseEntity<?> status(ServletRequest req, @RequestBody DiceRoleRequest gameToken){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        try{
            GameStatusResponse diceRoleRequest =gameSessionService.status(username,gameToken.getGameToken());
            return new ResponseEntity<>(diceRoleRequest, HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>("unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "/some-action/{target}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> someAction(@PathVariable String target) {
//    public ResponseEntity<?> someAction(@RequestBody String target) {

        // Do an action here
        // ...

        // Send the notification to "UserA" (by username)
//        notificationService.notify(
//                notification, // notification object
//                target.getUsername()                    // username
//        );

        messagingTemplate.convertAndSendToUser(
                target,
                "/queue/notify",
                "user"
        );

        // Return an http 200 status code
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
