package ir.game.services;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Game;

import ir.game.models.User;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.ResponseList;
import ir.game.models.beans.SliderGame;
import ir.game.repository.GameRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<SliderGame> getGamesForSlider(){
        ArrayList<SliderGame> al =new ArrayList<>();

        EntityManager em = emf.createEntityManager();
        Query q= em.createQuery("SELECT new ir.game.models.beans.SliderGame(s.game,avg(s.score)) from GameComment s join s.game " +
                "group by s.game.id order by avg(s.score) desc ");
        SliderGame game = (SliderGame) q.getSingleResult();
        game.setLabel("پرامتیاز ترین");
        game.getGame().setUser(null);
        game.getGame().setGameComments(null);
        game.getGame().setGameComments(null);
        al.add(game);
        em.close();


        final Game[] maxPlayingGame = new Game[1];
        List<Game> games = gameRepository.findAll();
        final int[] maxPlaying = {0};
        games.forEach((v)->{
            v.setUser(null);
            int number = this.gameSessionService.countPlyingSessins(v.getId());
            if(number>= maxPlaying[0]){
                maxPlaying[0] =number;
                maxPlayingGame[0] =v;
                maxPlayingGame[0].setGameComments(null);
            }
            v.setPlayingSessions(number);
        });
        al.add(new SliderGame(maxPlayingGame[0],"پرآنلاین ترین بازی"));

        em = emf.createEntityManager();
        q= em.createQuery("SELECT new ir.game.models.beans.SliderGame(s.game,avg(s.score)) from GameComment s join s.game " +
                "group by s.game.id order by s.game.date desc ");

        List<SliderGame> gameLists;
        gameLists=q.getResultList();
        gameLists.forEach(v->{
            v.getGame().setUser(null);
            v.getGame().setGameComments(null);
            v.getGame().setGameComments(null);
        });
        al.addAll(gameLists);
        em.close();

        return al;
    }

}
