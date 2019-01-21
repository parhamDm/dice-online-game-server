package ir.game.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserComment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)    private Long id;

    private Integer score;

    private String comment;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    private Long status;

    private LocalDateTime date;

    public UserComment(){
        date=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
