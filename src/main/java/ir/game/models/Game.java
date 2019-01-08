package ir.game.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Max(9999)
    private Integer scoreLimit;

    private ArrayList<Integer> currentZeroMaker;//numbers 123456

    private Integer numberOfDices; //1,2,4

    private String dicePerTurn;//number or "INF"

    @ManyToOne
    private User user;

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

    public String getDicePerTurn() {
        return dicePerTurn;
    }

    public void setDicePerTurn(String dicePerTurn) {
        this.dicePerTurn = dicePerTurn;
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
}
