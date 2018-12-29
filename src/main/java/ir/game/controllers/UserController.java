package ir.game.controllers;

import ir.game.models.User;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String signup(@RequestBody User user){
        return userService.signup(user);
    }




}
