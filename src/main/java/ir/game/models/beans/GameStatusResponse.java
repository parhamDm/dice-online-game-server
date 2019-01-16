package ir.game.models.beans;

import java.util.ArrayList;

public class GameStatusResponse {
    private String StatusCode;
    private String StatusDesc;
    private ArrayList<Integer> dices;
    private boolean yourTurn;
    private int current;
    private int score;
    private int opponentScore;
    private int opponentCurrent;


    private ArrayList<Integer>[] lastDice;

    public GameStatusResponse() {
        ArrayList<Integer>[] lastDice = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lastDice[i] = new ArrayList<Integer>();
        }
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusDesc() {
        return StatusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        StatusDesc = statusDesc;
    }

    public ArrayList<Integer> getDices() {
        return dices;
    }

    public void setDices(ArrayList<Integer> dices) {
        this.dices = dices;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public ArrayList<Integer> getLastDice(int index) {
        return lastDice[index];
    }

    public void setLastDice(int index, ArrayList<Integer> al) {
        this.lastDice[index] = al;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public int getOpponentCurrent() {
        return opponentCurrent;
    }

    public void setOpponentCurrent(int opponentCurrent) {
        this.opponentCurrent = opponentCurrent;
    }
}
