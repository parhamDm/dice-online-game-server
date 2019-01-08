package ir.game.services;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;
import ir.game.models.User;
import ir.game.models.beans.ResponseBean;
import ir.game.repository.GameRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;

    public ResponseBean create(String username, Game game){
        User user = userRepository.findFirstByUsername(username);
        if(user==null){
            return  new ResponseBean(-1,"کاربر نامعتبر است");
        }
        game.setUser(user);
        try {
            gameRepository.save(game);

        }catch (Exception ex){
            return  new ResponseBean(-1,"فرمت داده های ورودی نامعتبر است");
        }
        return  new ResponseBean(0,"DONE");

    }
}
