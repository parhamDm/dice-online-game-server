package ir.game.models.session;

import java.util.ArrayList;

public class PlayingSession {

    private Long P1Id;

    private Long P2Id;


    private String whosTurn;



    private Long gameId;

    private Integer scoreLimit;

    private ArrayList<Integer> currentZeroMaker;

    private Integer diceCount;

    private ArrayList<Integer>[] lastDice;

    private Integer dicePerRound;

    private Integer diceNumber;

    public PlayingSession() {
        lastDice = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            lastDice[i] = new ArrayList<Integer>();
        }
    }

    private int[] scores =new int[2];
    private int[] currents =new int[2];

    public Long getP1Id() {
        return P1Id;
    }

    public void setP1Id(Long p1Id) {
        P1Id = p1Id;
    }

    public Long getP2Id() {
        return P2Id;
    }

    public void setP2Id(Long p2Id) {
        P2Id = p2Id;
    }

    public String getWhosTurn() {
        return whosTurn;
    }

    public void setWhosTurn(String whosTurn) {
        this.whosTurn = whosTurn;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }


    public ArrayList<Integer> getCurrentZeroMaker() {
        return currentZeroMaker;
    }

    public void setCurrentZeroMaker(ArrayList<Integer> currentZeroMaker) {
        this.currentZeroMaker = currentZeroMaker;
    }

    public Integer getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(Integer diceCount) {
        this.diceCount = diceCount;
    }

    public Integer getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(Integer scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public int getScores(int index) {
        return scores[ index];
    }

    public void setScores(int index,int score) {
        this.scores[index] = score;
    }

    public int getCurrents(int index) {
        return currents[index];
    }

    public void setCurrents(int index,int currents) {
        this.currents[index] = currents;
    }

    public ArrayList<Integer> getLastDice(int index) {
        return lastDice[index];
    }

    public void setLastDice(int index, ArrayList<Integer> al) {
        this.lastDice[index] = al;
    }

    public void setLastDice(ArrayList<Integer>[] lastDice) {
        this.lastDice = lastDice;
    }

    public Integer getDicePerRound() {
        return dicePerRound;
    }

    public void setDicePerRound(Integer dicePerRound) {
        this.dicePerRound = dicePerRound;
    }

    public Integer getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(Integer diceNumber) {
        this.diceNumber = diceNumber;
    }
}
