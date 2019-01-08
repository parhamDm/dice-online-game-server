package ir.game.controllers;

import ir.game.models.ProfilePicture;
import ir.game.models.beans.LoginForm;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @RequestMapping(path = "/uploadPix",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {


        ProfilePicture dbFile = userService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploadPix/")
                .path(dbFile.getId()+"")
                .toUriString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "done";
    }



}
