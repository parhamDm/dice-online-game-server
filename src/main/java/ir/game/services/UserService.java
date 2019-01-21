package ir.game.services;

import ir.game.configuration.JwtTokenProvider;
import ir.game.models.GameFinished;
import ir.game.models.ProfilePicture;
import ir.game.models.Role;
import ir.game.models.User;
import ir.game.models.beans.*;
import ir.game.repository.GameFinishedRepository;
import ir.game.repository.ProfilePictureRepository;
import ir.game.repository.RoleRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    GameFinishedRepository gameFinishedRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;
    @PersistenceUnit
    private EntityManagerFactory emf;

    public TokenResponse signup(UserRegisterForm form) {
        User user =new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setAvgScore(0D);
        user.setPlays(0);

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

    public User getUserInfo(String username) {
        User taken = userRepository.findFirstByUsername(username);
        if(taken!=null){
            taken.setPassword(null);
        }
        return taken;
    }

    @Transactional
    public TokenResponse updateProfile(UserRegisterForm form, String username) {


        User user = userRepository.findFirstByUsername(username);
        if(!inNull(form.getFirstName())){
            user.setFirstName(form.getFirstName());
        }

        if(!inNull(form.getLastName())){
            user.setLastName(form.getLastName());
        }

        if(!inNull(form.getUsername())){
            if(!username.equals(form.getUsername())) {
                User taken = userRepository.findFirstByUsername(form.getUsername());
                if (taken != null) {
                    TokenResponse tokenResponse = new TokenResponse();
                    tokenResponse.setErrorMsg("نام کاربری قبلا استفاده شده است");
                    tokenResponse.setToken("INVALID");
                    return tokenResponse;
                }
            }
            user.setUsername(form.getUsername());
        }

        if(!inNull(form.getPassword())){
            user.setPassword(form.getPassword());
        }

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
        tokenResponse.setUsername(user.getUsername());
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

    public User storeFile(MultipartFile file,String username) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        User user = userRepository.findFirstByUsername(username);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            ProfilePicture dbFile = new ProfilePicture(fileName, file.getContentType(), file.getBytes());
            profilePictureRepository.save(dbFile);

            user.setProfilePicture(dbFile);

            return userRepository.save(user);
        } catch (IOException ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public List<UserData> userList(){
        List<User> users=userRepository.findAll();
        ArrayList<UserData> al =new ArrayList<>();

        for (User user:users
             ) {
            user.setPassword(null);
            user.setFriends(null);
            double score = 0;
            try {
                score =this.getUserScore(user.getUsername());
            }catch (Exception ex){
                System.out.println("unable to get score data");
            }
            al.add(new UserData(user,score));
            //get number of played games
            int countOfPlayedGames = gameFinishedRepository.findSpecifiedUserGames(user.getUsername()).size();
            //get average score
//            String quer="SELECT AVG ()" +
//                    "(SELECT gs.player1score sc" +
//                    "from user u" +
//                    " JOIN game_session gs on gs.player1_id=u.id " +
//                    " WHERE gs.game_status='OVER' and u.id =:userId )" +
//                    "UNION " +
//                    "(SELECT  gsplayer2score " +
//                    "from user u" +
//                    " JOIN game_session gs on gs.player2_id=u.id" +
//                    " WHERE gs.game_status='OVER' and u.id =:userId)";
//            System.out.println(quer);
//            Query q= em.createNativeQuery(quer);
//            q.setParameter("userId",user.getId());
//            List authors = q.getResultList();
        }
        return al;
    }

    public ResponseList<?> gamesPlayed(String username){
        List<GameFinished> gameSessions= gameFinishedRepository.findSpecifiedUserGames(username);
        ArrayList<GameHistory> ghs=new ArrayList<GameHistory>();
        for (GameFinished gs:gameSessions) {
            GameHistory gh=new GameHistory();
            String user = gs.getPlayer1().getUsername();
            if(user.equals(username)){
                gh.setOpponent(gs.getPlayer2().getUsername());
            }else{
                gh.setOpponent(gs.getPlayer1().getUsername());
            }
            gh.setComments(gs.getGame().getGameComments());
            gh.setDatePlayed(gs.getPlayTime());
            gh.setWinner(gs.getWinner());
            ghs.add(gh);
        }
        return new ResponseList<GameHistory>(0,"DONE",ghs);
    }


    public ProfilePicture getFile(String username) {
        try {
            return userRepository.findFirstByUsername(username).getProfilePicture();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Transactional
    public double getUserScore(String username){
        EntityManager em = emf.createEntityManager();
            Query q= em.createQuery("SELECT avg(s.score) from UserComment s join s.to where " +
                    "s.to.username = :userId" +
                    " and s.status   = 1");
            q.setParameter("userId",username);
            Double count = (Double) q.getSingleResult();
            em.close();
            if (count ==null) count=0.0;
            return count;
    }

    private boolean inNull(String s){
        if(s==null || s.equals("")){
            return true;
        }
        return false;
    }

}
