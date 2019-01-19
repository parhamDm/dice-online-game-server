package ir.game.models.beans;

import java.time.LocalDateTime;

public class UserComments {

    private Long id;

    private String fromUsername;

    private String toUsername;

    private Integer score;

    private String comment;

    private Long status;

    private LocalDateTime date;


    public UserComments(Long id,String fromUsername, String toUsername, Integer score, String comment, Long status, LocalDateTime date) {
        this.id=id;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.score = score;
        this.comment = comment;
        this.status = status;
        this.date = date;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
