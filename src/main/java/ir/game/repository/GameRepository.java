package ir.game.repository;

import ir.game.models.Game;
import ir.game.models.ProfilePicture;
import ir.game.models.Role;
import ir.game.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByUser(User user);

}
