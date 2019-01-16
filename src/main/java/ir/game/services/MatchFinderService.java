package ir.game.services;

import ir.game.models.Game;
import ir.game.models.GameSession;
import ir.game.models.User;
import ir.game.models.beans.ResponseBean;
import ir.game.models.session.PlayingSession;
import ir.game.repository.GameRepository;
import ir.game.repository.UserRepository;
import ir.game.util.GameQueue;
import ir.game.util.GameSessionHashMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MatchFinderService {

    @Autowired
    private GameQueue<QueueItems> queue;

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public  ResponseBean addToQueue(String username,Long gameId){
        User user=userRepository.findFirstByUsername(username);
        Game game = gameRepository.getOne(gameId);
        if(game==null){
            return new ResponseBean(2,"Game note found");
        }

        if(queue.contains(new QueueItems(user.getId(),LocalDateTime.now(),gameId))){
            return new ResponseBean(1,"Already in queue");
        }
        QueueItems queueItems=new QueueItems(user.getId(),LocalDateTime.now(),gameId);
        queue.add(queueItems);
        return new ResponseBean(0,"Added to queue");
    }

    public ResponseBean statusOfRequest(String username){
        Long id=userRepository.findFirstByUsername(username).getId();
        if(queue.contains(new QueueItems(id,null,null))){
            return new ResponseBean(1,"Already in queue");
        }

        GameSessionHashMap<String,PlayingSession> hm = gameSessionService.getGames();
        final boolean[] bool = new boolean[1];
        final String[] key = new String[1];
        hm.forEach((k,v)->{
            if(Objects.equals(v.getP1Id(), id)||Objects.equals(v.getP2Id(), id)){
                bool[0] =true;
                key[0] =k;
            }
        });
        if (bool[0]){
            return new ResponseBean(0,key[0]);

        }

        return new ResponseBean(2,"RquestExpired");
    }

    @Scheduled(fixedRate = 5000)
    public void matchMaker(){
        if(queue.size()<1){
            return;
        }
        //remove expired
        Iterator<QueueItems> i = queue.iterator();
        while(i.hasNext()) {
            QueueItems qi  = i.next();
            LocalDateTime localDateTime = qi.getTimeAddedToQueue().minusMinutes(2);
            int compare = localDateTime.compareTo(LocalDateTime.now());
            if(compare>0){
                queue.remove(i);
            }
        }
        //try to find match
        findSameGame();
    }

    private void findSameGame(){

        Iterator<QueueItems> i = queue.iterator();
        Iterator<QueueItems> j = queue.iterator();
        while (i.hasNext()) {
            QueueItems f=i.next();
            while(j.hasNext()) {
                QueueItems s = j.next();

                if(Objects.equals(f.gameId, s.gameId)&& !Objects.equals(f.id, s.id)) {
                    makeGame(f, s);
                    queue.remove(f);
                    queue.remove(s);
                }

            }
        }

    }

    private void makeGame(QueueItems f, QueueItems s) {
        PlayingSession playingSession=new PlayingSession();
        String gameToken = RandomStringUtils.randomAlphanumeric(16);
        Game game = gameRepository.findById(f.getGameId()).orElse(null);
        if(game==null){
            return;
        }

        ArrayList<Integer> al=new ArrayList<>();
        al.add(6);

        playingSession.setP1Id(f.getId());
        playingSession.setP2Id(s.getId());
        playingSession.setGameId(f.getGameId());
        playingSession.setWhosTurn("P1");
        playingSession.setScoreLimit(game.getScoreLimit());
        playingSession.setCurrentZeroMaker(al);
//        playingSession.setCurrentZeroMaker(game.getCurrentZeroMaker());
        playingSession.setDiceCount(game.getNumberOfDices());

        GameSessionHashMap gsm = gameSessionService.getGames();
        gsm.put(gameToken,playingSession);

    }


    private class QueueItems{
        private Long id;
        private Long gameId;
        private LocalDateTime timeAddedToQueue;

        public QueueItems(Long id, LocalDateTime timeAddedToQueue,Long gameId) {
            this.id = id;
            this.timeAddedToQueue = timeAddedToQueue;
            this.gameId=gameId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public LocalDateTime getTimeAddedToQueue() {
            return timeAddedToQueue;
        }

        public void setTimeAddedToQueue(LocalDateTime timeAddedToQueue) {
            this.timeAddedToQueue = timeAddedToQueue;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof QueueItems)) return false;

            QueueItems that = (QueueItems) o;

            return getId().equals(that.getId());
        }

        @Override
        public int hashCode() {
            return getId().hashCode();
        }

        public Long getGameId() {
            return gameId;
        }

        public void setGameId(Long gameId) {
            this.gameId = gameId;
        }
    }



}
