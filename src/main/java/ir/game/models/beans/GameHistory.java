package ir.game.models.beans;

import java.time.LocalDateTime;
import java.util.List;

public class GameHistory {
    private String opponent;
    private LocalDateTime datePlayed;
    private String winner;
    private List comments;
    public GameHistory(){}

    public GameHistory(String oppenent, LocalDateTime dateplayed, String winner, List comments) {
        this.opponent = oppenent;
        this.datePlayed = dateplayed;
        this.winner = winner;
        this.comments = comments;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public LocalDateTime getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(LocalDateTime datePlayed) {
        this.datePlayed = datePlayed;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List getComments() {
        return comments;
    }

    public void setComments(List comments) {
        this.comments = comments;
    }
}
