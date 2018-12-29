package ir.game.models;

import javax.persistence.*;

@Entity
public class GameSession {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private User player1;

    @ManyToOne
    private User player2;

    private int player1Score;

    private int player2Score;

    private int player1Current;

    private int player2Current;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public int getPlayer1Current() {
        return player1Current;
    }

    public void setPlayer1Current(int player1Current) {
        this.player1Current = player1Current;
    }

    public int getPlayer2Current() {
        return player2Current;
    }

    public void setPlayer2Current(int player2Current) {
        this.player2Current = player2Current;
    }
}