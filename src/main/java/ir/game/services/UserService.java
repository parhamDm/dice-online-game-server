package ir.game.services;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.Role;
import ir.game.models.User;
import ir.game.models.beans.TokenResponse;
import ir.game.models.beans.UserRegisterForm;
import ir.game.repository.RoleRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenResponse signup(UserRegisterForm form) {
        User user =new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        User taken = userRepository.findFirstByUsername(form.getUsername());
        if(taken!=null){
            TokenResponse tokenResponse=new TokenResponse();
            tokenResponse.setErrorMsg("نام کاربری قبلا استفاده شده است");
            tokenResponse.setToken("INVALID");
            return tokenResponse;
        }
        /*
         * create role
         */
        Role role;
        try{
            role = roleRepository.findFirstByRoleName("normal-user");
            if(role==null){
                role=new Role();
                role.setRoleName("normal-user");
                roleRepository.save(role);
            }
        }
        catch (Exception ex){
            role=null;
        }

        user.setRole(role);

        try {
            user.setPassword(getMD5(user.getPassword()));

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        userRepository.save(user);

        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setRole(user.getRole().getRoleName());
        tokenResponse.setToken(jwtTokenProvider.createToken(user.getUsername(), "normal-user"));
        tokenResponse.setUsername(user.getUsername());
        return tokenResponse;

    }

    public TokenResponse login(String username, String password){
        TokenResponse tokenResponse=new TokenResponse();
        String secret="";
        try {
            secret = getMD5(password);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        User user = userRepository.findFirstByUsernameAndPassword(username,secret);
        if(user==null){
            tokenResponse.setToken("INVALID");
            return tokenResponse;
        }
        if (user.getRole()==null){
            tokenResponse.setRole("ROLE_GUEST");
        }else{
            tokenResponse.setRole("ROLE_"+user.getRole().getRoleName());
        }
        String token=jwtTokenProvider.createToken(user.getUsername(), "user");
        tokenResponse.setToken(token);
        return tokenResponse;
    }

    public static String getMD5(String data) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest=MessageDigest.getInstance("MD5");

        messageDigest.update(data.getBytes());
        byte[] digest=messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString((int) (b & 0xff)));
        }
        return sb.toString();
    }
    public void updateLastLoggedIn(String username){
        User user = userRepository.findFirstByUsername(username);
        if(user==null){
            return;
        }
        user.setLastRequest(LocalDateTime.now());
        userRepository.save(user);
    }

}
