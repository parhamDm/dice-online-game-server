package ir.game.repository;

import ir.game.models.Role;
import ir.game.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByRoleName(String roleName);
}
