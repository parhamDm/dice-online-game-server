package ir.game.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Size(max = 9999)
    private int scoreLimit;

    private String currentZeroMaker;//numbers 123456

    @Size(max = 4, min=1)
    private int numberOfDices; //1,2,4

    private String dicePerTurn;//number or "INF"

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

    public String getCurrentZeroMaker() {
        return currentZeroMaker;
    }

    public void setCurrentZeroMaker(String currentZeroMaker) {
        this.currentZeroMaker = currentZeroMaker;
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
}
