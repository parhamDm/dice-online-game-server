package ir.game.models.beans;

import java.time.LocalDateTime;

public class GameComments {
    private Long id;

    private int score;

    private String comment;

    private String username;

    private String GameName;

    private LocalDateTime date;

    private Long status;

    public GameComments(Long id, int score, String comment, String username, String gameName,LocalDateTime localDateTime,Long status) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.username = username;
        GameName = gameName;
        this.date=localDateTime;
        this.status=status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
