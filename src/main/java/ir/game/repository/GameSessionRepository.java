package ir.game.repository;

import ir.game.models.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public  interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    @Query("select gs from GameSession gs where " +
            "(gs.player1.username = ?2 or gs.player2.username = ?2) and gs.gameStatus=?1" +
            " order by gs.playTime desc")
    List<GameSession> findSpecifiedUserGames(String status,String username);
}
