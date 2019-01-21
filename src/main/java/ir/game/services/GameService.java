package ir.game.services;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;

import ir.game.models.User;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.ResponseList;
import ir.game.repository.GameRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    GameSessionService gameSessionService;

    @PersistenceUnit
    private EntityManagerFactory emf;

    public ResponseBean create(String username, Game game){
        User user = userRepository.findFirstByUsername(username);
        if(user==null){
            return  new ResponseBean(-1,"کاربر نامعتبر است");
        }
        game.setUser(user);
        game.setGamesWon(0);
        game.setUsername(user.getUsername());
        game.setDate(LocalDateTime.now());
        try {
            gameRepository.save(game);

        }catch (Exception ex){
            return  new ResponseBean(-1,"فرمت داده های ورودی نامعتبر است");
        }
        return  new ResponseBean(0,"DONE");
    }



    public ResponseList listOfOwnGames(String username){
        User user = userRepository.findFirstByUsername(username);
        if(user==null){
            return  new ResponseList(-1,"کاربر نامعتبر است",null);
        }
        List<Game> games = gameRepository.findAllByUser(user);
        for (Game game: games){
            game.setUsername(game.getUser().getUsername());
            game.setUser(null);
        }
        ResponseList<Game> responseList =new ResponseList<Game>(0,"DONE",games);
        return responseList;
    }

    public ResponseList listOfAllGames(){

        List<Game> games = gameRepository.findAll();
        games.forEach((v)->{
            v.setUser(null);
            int number = this.gameSessionService.countPlyingSessins(v.getId());
            v.setPlayingSessions(number);
        });
        ResponseList<Game> responseList =new ResponseList<Game>(0,"DONE",games);

        return responseList;
    }

    public Game get(Long id){
        Optional<Game> game;
        game = gameRepository.findById(id);
        return game.orElse(null);
    }

}
