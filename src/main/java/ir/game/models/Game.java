package ir.game.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Max(9999)
    private Integer scoreLimit;

    private ArrayList<Integer> currentZeroMaker;//numbers 123456

    private Integer numberOfDices; //1,2,4

    private Integer dicePerTurn;//number or null

    private String username;

    private String gameName;

    private LocalDateTime date;

    private Integer gamesWon;

    @Transient
    private int playingSessions;

    @ManyToOne
    private User user;

    @OneToMany
    private List<GameComment> gameComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(Integer scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public ArrayList<Integer> getCurrentZeroMaker() {
        return currentZeroMaker;
    }

    public void setCurrentZeroMaker(ArrayList<Integer> currentZeroMaker) {
        this.currentZeroMaker = currentZeroMaker;
    }

    public Integer getNumberOfDices() {
        return numberOfDices;
    }

    public void setNumberOfDices(Integer numberOfDices) {
        this.numberOfDices = numberOfDices;
    }

    public Integer getDicePerTurn() {
        return dicePerTurn;
    }

    public void setDicePerTurn(Integer dicePerTurn) {
        this.dicePerTurn = dicePerTurn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getPlayingSessions() {
        return playingSessions;
    }

    public void setPlayingSessions(int playingSessions) {
        this.playingSessions = playingSessions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GameComment> getGameComments() {
        return gameComments;
    }

    public void setGameComments(List<GameComment> gameComments) {
        this.gameComments = gameComments;
    }
}
