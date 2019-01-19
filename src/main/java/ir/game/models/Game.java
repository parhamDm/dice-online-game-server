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

    private Integer timesPlayed;

    @Transient
    private int playingSessions;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany
    private List<GameComment> gameComments;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(int scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public int getNumberOfDices() {
        return numberOfDices;
    }

    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDices = numberOfDices;
    }


    public ArrayList<Integer> getCurrentZeroMaker() {
        return currentZeroMaker;
    }

    public void setCurrentZeroMaker(ArrayList<Integer> currentZeroMaker) {
        this.currentZeroMaker = currentZeroMaker;
    }

    public User getUser() {
        return user;

    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GameComment> getGameComments() {
        return gameComments;
    }

    public void setGameComments(List<GameComment> gameComments) {
        this.gameComments = gameComments;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getDicePerTurn() {
        return dicePerTurn;
    }

    public void setDicePerTurn(Integer dicePerTurn) {
        this.dicePerTurn = dicePerTurn;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public int getPlayingSessions() {
        return playingSessions;
    }

    public void setPlayingSessions(int playingSessions) {
        this.playingSessions = playingSessions;
    }
}
