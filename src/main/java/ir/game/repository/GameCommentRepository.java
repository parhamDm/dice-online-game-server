package ir.game.repository;

import ir.game.models.GameComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCommentRepository extends JpaRepository<GameComment, Long> {

}
