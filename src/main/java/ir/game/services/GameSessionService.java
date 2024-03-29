package ir.game.services;

import ir.game.models.Game;
import ir.game.models.GameFinished;
import ir.game.models.User;
import ir.game.models.beans.GameStatusResponse;
import ir.game.models.session.PlayingSession;
import ir.game.repository.GameFinishedRepository;
import ir.game.repository.GameRepository;
import ir.game.repository.UserRepository;
import ir.game.util.GameSessionHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Service
public class GameSessionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameFinishedRepository gameFinishedRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    private GameSessionHashMap<String,PlayingSession> games;

    public synchronized GameStatusResponse dice(String username, String gameToken){
        GameStatusResponse gameStatusResponse =new GameStatusResponse();

        int userId = Math.toIntExact(userRepository.findFirstByUsername(username).getId());

        PlayingSession playingSession=games.get(gameToken);

        int userNum;
        int opponentNum=0;

        //validate turn
        if(userId==playingSession.getP1Id()){
            userNum=0;
            opponentNum=1;
        }else {
            userNum=1;
            opponentNum=0;
        }
        if(userNum==1 && playingSession.getWhosTurn().equals("P1")){
            gameStatusResponse.setStatusCode("1");
            gameStatusResponse.setStatusDesc("not your turn");
            return gameStatusResponse;
        }
        if(userNum==0 && playingSession.getWhosTurn().equals("P2")){
            gameStatusResponse.setStatusCode("1");
            gameStatusResponse.setStatusDesc("not your turn");
            return gameStatusResponse;
        }
        //roll
        ArrayList<Integer> dices=new ArrayList<>();
        Random r =new Random();
        int sum=0;
        for (int i = 0; i < playingSession.getDiceCount(); i++) {
            int roll = r.nextInt(6) + 1;
            sum+=roll;
            dices.add(roll);
        }
        playingSession.setDiceNumber(playingSession.getDiceNumber()+1);
        //current value
        int finalSum = sum;
        final boolean[] status = new boolean[1];
        playingSession.getCurrentZeroMaker().forEach((v)->{
            if(v== finalSum){
                status[0] =true;
                playingSession.setCurrents(userNum,0);
                if(userNum==0){
                    playingSession.setWhosTurn("P2");
                    gameStatusResponse.setCurrent(0);
                }else {
                    playingSession.setWhosTurn("P1");
                    gameStatusResponse.setCurrent(0);
                }
            }

        });

        //update last dice values
        playingSession.setLastDice(0,dices);

        gameStatusResponse.setDices(dices);

        gameStatusResponse.setOpponentScore(playingSession.getScores(opponentNum));
        gameStatusResponse.setOpponentCurrent(playingSession.getCurrents(opponentNum));
        gameStatusResponse.setOpponentScore(playingSession.getScores(opponentNum));
        //courrent == dices

        if(status[0]){
            gameStatusResponse.setYourTurn(false);
        }else{
            int score = sum + playingSession.getCurrents(userNum);
            playingSession.setCurrents(userNum,score);
            gameStatusResponse.setCurrent(score);
            gameStatusResponse.setYourTurn(true);
        }

        //dice limit
        if(playingSession.getDicePerRound()!=null&&!status[0]&&
                playingSession.getDicePerRound() <= playingSession.getDiceNumber() ){
            return hold(username,gameToken);
        }
        gameStatusResponse.setScore(playingSession.getScores(userNum));
        gameStatusResponse.setStatusCode("0");
        gameStatusResponse.setStatusDesc("DONE");
        return gameStatusResponse;

    }

    public GameStatusResponse hold(String username, String gameToken){
        GameStatusResponse gameStatusResponse =new GameStatusResponse();

        int userId = Math.toIntExact(userRepository.findFirstByUsername(username).getId());

        PlayingSession playingSession=games.get(gameToken);
        playingSession.setDiceNumber(0);
        int userNum;
        int opponentNum;

        //validate turn
        if(userId==playingSession.getP1Id()){
            userNum=0;
            opponentNum=1;
        }else {
            userNum=1;
            opponentNum=0;
        }
        if(userNum==1 && playingSession.getWhosTurn().equals("P1")){
            gameStatusResponse.setStatusCode("1");
            gameStatusResponse.setStatusDesc("not your turn");
            return gameStatusResponse;
        }
        if(userNum==0 && playingSession.getWhosTurn().equals("P2")){
            gameStatusResponse.setStatusCode("1");
            gameStatusResponse.setStatusDesc("not your turn");
            return gameStatusResponse;
        }

        //updating session
        int current =  playingSession.getCurrents(userNum);
        int score =  playingSession.getScores(userNum);
        playingSession.setScores(userNum,score+current);
        playingSession.setCurrents(userNum,0);
        playingSession.setWhosTurn("P"+(opponentNum+1));


        //responseBean
        gameStatusResponse.setOpponentScore(playingSession.getScores(opponentNum));
        gameStatusResponse.setOpponentCurrent(playingSession.getCurrents(opponentNum));
        gameStatusResponse.setCurrent(0);
        gameStatusResponse.setOpponentCurrent(0);
        gameStatusResponse.setScore(playingSession.getScores(userNum));
        gameStatusResponse.setYourTurn(false);
        gameStatusResponse.setStatusCode("0");
        gameStatusResponse.setStatusDesc("DONE");
        gameStatusResponse.setDices(playingSession.getLastDice(0));
        //winner Status
        if(score+current>=playingSession.getScoreLimit()){
            gameIsOver((long) userId,playingSession,gameToken);
            this.games.remove(gameToken);
        }
        return gameStatusResponse;
    }

    public GameStatusResponse status(String username, String gameToken){
        GameStatusResponse gameStatusResponse =new GameStatusResponse();

        int userId = Math.toIntExact(userRepository.findFirstByUsername(username).getId());

        PlayingSession playingSession=games.get(gameToken);
        if(playingSession==null){
            GameFinished gameFinished=gameFinishedRepository.findFirstByGameToken(gameToken);
            if(gameFinished.getWinner().equals(username)){
                gameStatusResponse.setStatusCode("4");
                gameStatusResponse.setStatusDesc("won");
            }else {
                gameStatusResponse.setStatusCode("5");
                gameStatusResponse.setStatusDesc("lost");
            }
            return gameStatusResponse;
        }

        int userNum;
        int opponentNum;

        //validate turn
        if(userId==playingSession.getP1Id()){
            userNum=0;
            opponentNum=1;
            gameStatusResponse.setOpponentId(playingSession.getP2Id());
        }else {
            userNum=1;
            opponentNum=0;
            gameStatusResponse.setOpponentId(playingSession.getP1Id());
        }
        if((userNum==1 && playingSession.getWhosTurn().equals("P1"))
        ||(userNum==0 && playingSession.getWhosTurn().equals("P2"))){
            gameStatusResponse.setYourTurn(false);

        }else{
            gameStatusResponse.setYourTurn(true);

        }
        gameStatusResponse.setOpponentScore(playingSession.getScores(opponentNum));
        gameStatusResponse.setOpponentCurrent(playingSession.getCurrents(opponentNum));
        gameStatusResponse.setDices(playingSession.getLastDice(0));
        gameStatusResponse.setScore(playingSession.getScores(userNum));
        gameStatusResponse.setCurrent(playingSession.getScores(userNum));

        return gameStatusResponse;
    }

    private void gameIsOver(Long userId,PlayingSession playingSession,String gameToken) {
        User user1=userRepository.findFirstById(playingSession.getP1Id());
        User user2=userRepository.findFirstById(playingSession.getP2Id());
        User user3=userRepository.findFirstById(userId);
        Game game = gameRepository.findById(playingSession.getGameId()).orElse(null);
        GameFinished gameFinished=new GameFinished();
        gameFinished.setPlayer1(user1);
        gameFinished.setPlayer1(user2);
        gameFinished.setGame(game);
        gameFinished.setPlayer1Score(playingSession.getScores(0));
        gameFinished.setPlayer2Score(playingSession.getScores(1));
        gameFinished.setPlayTime(LocalDateTime.now());
        gameFinished.setPlayTime(LocalDateTime.now());
        gameFinished.setWinner(user3.getUsername());
        gameFinished.setGameToken(gameToken);
        gameFinishedRepository.save(gameFinished);

        user1.setPlays(user1.getPlays()+1);
        user2.setPlays(user2.getPlays()+1);
        userRepository.save(user1);
        userRepository.save(user2);

        game.setGamesWon(game.getGamesWon()+1);
        gameRepository.save(game);
    }

    public int countPlyingSessins(Long gameId){
        final int[] sum = {0};
        this.games.forEach((v,e)->{
            if(e.getGameId()==gameId){
                sum[0]++;
            }
        });
        return sum[0];
    }

    public GameSessionHashMap<String, PlayingSession> getGames() {
        return games;
    }

    public void setGames(GameSessionHashMap<String, PlayingSession> games) {
        this.games = games;
    }
}
