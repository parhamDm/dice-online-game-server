package ir.game.models.beans;

public class Comment {

    private int userScore;
    private String userComment;
    private String gameComment;
    private int gameScore;
    private int gameId;
    private Long toUserId;

    public Comment() {
    }


    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getGameComment() {
        return gameComment;
    }

    public void setGameComment(String gameComment) {
        this.gameComment = gameComment;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
