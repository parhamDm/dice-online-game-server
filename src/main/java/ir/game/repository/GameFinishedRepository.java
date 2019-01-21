package ir.game.repository;

import ir.game.models.GameFinished;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public  interface GameFinishedRepository extends JpaRepository<GameFinished, Long> {

    @Query("select gs from GameFinished gs where " +
            "(gs.player1.username = ?1 or gs.player2.username = ?1)" +
            " order by gs.playTime desc")
    List<GameFinished> findSpecifiedUserGames(String username);

    GameFinished findFirstByGameToken(String gameToken);
}
