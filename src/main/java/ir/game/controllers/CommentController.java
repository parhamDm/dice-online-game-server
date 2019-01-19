package ir.game.controllers;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;
import ir.game.models.beans.ResponseBean;
import ir.game.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = "/unAprrovedGame",method = RequestMethod.GET)
    public ResponseEntity<?> create(){

        ResponseBean responseBean=commentService.ListUnApprovedGameComment();
        return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
    }

    @RequestMapping(path = "/listOfGameComment",method = RequestMethod.GET)
    public ResponseEntity<?> listOfGameComment(){

        ResponseBean responseBean=commentService.listOfGameComment();
        return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
    }

    @RequestMapping(path = "/changeStatusGame",method = RequestMethod.GET)
    public ResponseEntity<?> changeStatusGame(ServletRequest req, @RequestParam Long commentId,@RequestParam Long status){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean=commentService.approveGameComment(username,commentId,status);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }

    @RequestMapping(path = "/unApprovedUserComment",method = RequestMethod.GET)
    public ResponseEntity<?> unApprovedUserComment(){

        ResponseBean responseBean=commentService.listOfUnApprovedUserComment();
        return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
    }

    @RequestMapping(path = "/listOfUserComment",method = RequestMethod.GET)
    public ResponseEntity<?> listOfUserComment(){

        ResponseBean responseBean=commentService.listOfUserComment();
        return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
    }

    @RequestMapping(path = "/changeStatusUser",method = RequestMethod.GET)
    public ResponseEntity<?> changeStatusUser(ServletRequest req, @RequestParam Long commentId,@RequestParam Long status){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        String username=jwtTokenProvider.getUsername(token);
        ResponseBean responseBean=commentService.approveUserComment(username,commentId,status);
        return new ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
    }
}
