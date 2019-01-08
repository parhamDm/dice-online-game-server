package ir.game.repository;

import ir.game.models.ProfilePicture;
import ir.game.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
}
