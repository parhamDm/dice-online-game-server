package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.ProfilePicture;
import ir.game.models.beans.LoginForm;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

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

    @RequestMapping(path = "/updateProfile",method = RequestMethod.POST)
    public ResponseEntity<?> updateProfile(ServletRequest req,@RequestBody UserRegisterForm user){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        return new ResponseEntity<>(userService.updateProfile(user,username),HttpStatus.OK);
    }

    @RequestMapping(path = "/info",method = RequestMethod.GET)
    public ResponseEntity<?> updateProfile(ServletRequest req){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        return new ResponseEntity<>(userService.getUserInfo(username),HttpStatus.OK);
    }


    @RequestMapping(path = "/uploadPix",method = RequestMethod.POST)
    public String uploadFile(ServletRequest req,@RequestParam("file") MultipartFile file) {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            String username=jwtTokenProvider.getUsername(token);

        userService.storeFile(file,username);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/uploadPix/")
//                .path(dbFile.getId()+"")
//                .toUriString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "done";
    }

    @RequestMapping(path = "/profile/{fileId}",method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        ProfilePicture dbFile = userService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    @RequestMapping(path = "/userList",method = RequestMethod.GET)
    public ResponseEntity<?> downloadFile() {
        // Load file from database

        return ResponseEntity.ok()
                .body(userService.userList());
    }


}
