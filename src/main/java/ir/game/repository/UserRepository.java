package ir.game.repository;

import ir.game.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);
    User findFirstByUsernameAndPassword(String username,String password);
}
