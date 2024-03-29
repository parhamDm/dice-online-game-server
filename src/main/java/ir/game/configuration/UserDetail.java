package ir.game.configuration;

import ir.game.models.Role;
import ir.game.models.User;
import ir.game.repository.UserRepository;
import ir.game.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetail{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findFirstByUsername(username);

        //update request Value
        userService.updateLastLoggedIn(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        if(user.getRole()==null){
            user.setRole(new Role("ROLE_GUEST"));
        }else{
            user.getRole().setRoleName("ROLE_"+(user.getRole().getRoleName()));
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.getPassword())//
                .authorities(user.getRole().getRoleName())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
