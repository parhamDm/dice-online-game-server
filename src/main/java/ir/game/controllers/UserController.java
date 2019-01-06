package ir.game.controllers;

import ir.game.models.beans.LoginForm;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(path = "/generateToken",method = RequestMethod.POST)
    public String login(){
        return "login works";
    }

    @RequestMapping(path = "/signup",method = RequestMethod.POST)
    public ResponseEntity<?>  signup(@RequestBody UserRegisterForm user){
        return new ResponseEntity<TokenResponse>(userService.signup(user),HttpStatus.OK);
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginForm user){
        return new ResponseEntity<TokenResponse>(userService.login(user.getUsername(),user.getPassword()),HttpStatus.OK);
    }



}
