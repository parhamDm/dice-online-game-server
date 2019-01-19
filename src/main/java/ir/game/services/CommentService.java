package ir.game.services;

import ir.game.models.*;
import ir.game.models.beans.GameComments;
import ir.game.models.beans.ResponseBean;
import ir.game.models.beans.ResponseList;
import ir.game.models.beans.UserComments;
import ir.game.repository.GameCommentRepository;
import ir.game.repository.GameRepository;
import ir.game.repository.UserCommentRepository;
import ir.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    GameCommentRepository gameCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserCommentRepository userCommentRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    /**
     *
     * @param status 1 is approve, 2 is declined
     * @return
     */
    public ResponseBean approveGameComment(String username,Long commnetID,Long status){
        User u = userRepository.findFirstByUsername(username);
        if(u==null||!u.getRole().getRoleName().equals("admin")){
            return new ResponseBean(-1,"you are not admin");
        }

        GameComment gameComment = gameCommentRepository.findById(commnetID).orElse(null);
        if(gameComment ==null){
            return new ResponseBean(-1,"not found");
        }

        gameComment.setStatus(status);
        gameCommentRepository.save(gameComment);
        return new ResponseBean(0,"Done");
    }

    public ResponseList<?> ListUnApprovedGameComment(){

        EntityManager em = emf.createEntityManager();
        Query q= em.createQuery("SELECT new ir.game.models.beans.GameComments(gc.id,gc.score,gc.comment,u.username,g.gameName,gc.date,gc.status)" +
                " from GameComment gc left join gc.user u left join gc.game g " +
                " where gc.status = 0 ");
        List<GameComments> gc = q.getResultList();
        em.close();

        ResponseList<GameComment> rl=new ResponseList(0,"DONE",gc);
        return rl;
    }

    public ResponseList<?> listOfGameComment(){

        EntityManager em = emf.createEntityManager();
        Query q= em.createQuery("SELECT new ir.game.models.beans.GameComments(gc.id,gc.score,gc.comment,u.username,g.gameName,gc.date,gc.status)" +
                " from GameComment gc left join gc.user u left join gc.game g ");
        List<GameComments> gc = q.getResultList();
        em.close();

        ResponseList<GameComment> rl=new ResponseList(0,"DONE",gc);
        return rl;
    }

    public ResponseBean approveUserComment(String username,Long commnetID,Long status){
        User u = userRepository.findFirstByUsername(username);
        if(u==null||!u.getRole().getRoleName().equals("admin")){
            return new ResponseBean(-1,"you are not admin");
        }

        UserComment userComment = userCommentRepository.findById(commnetID).orElse(null);
        if(userComment ==null){
            return new ResponseBean(-1,"not found");
        }

        userComment.setStatus(status);
        userCommentRepository.save(userComment);
        return new ResponseBean(0,"Done");
    }

    public ResponseList<?> listOfUserComment(){

        EntityManager em = emf.createEntityManager();
        Query q= em.createQuery("SELECT new ir.game.models.beans.UserComments(uc.id,uf.username,ut.username,uc.score,uc.comment,uc.status,uc.date)" +
                " from UserComment uc left join uc.from uf left join uc.to ut ");
        List<GameComments> gc = q.getResultList();
        em.close();

        ResponseList<GameComment> rl=new ResponseList(0,"DONE",gc);
        return rl;
    }

    public ResponseList<?> listOfUnApprovedUserComment(){

        EntityManager em = emf.createEntityManager();
        Query q= em.createQuery("SELECT new ir.game.models.beans.UserComments(uc.id,uf.username,ut.username,uc.score,uc.comment,uc.status,uc.date)" +
                " from UserComment uc left join uc.from uf left join uc.to ut " +
                "where uc.status=0");
        List<GameComments> gc = q.getResultList();
        em.close();

        ResponseList<UserComments> rl=new ResponseList(0,"DONE",gc);
        return rl;
    }
}
