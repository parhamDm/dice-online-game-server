package ir.game.repository;

import ir.game.models.Game;
import ir.game.models.ProfilePicture;
import ir.game.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
