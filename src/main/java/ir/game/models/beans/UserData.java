package ir.game.models.beans;

import ir.game.models.User;

import java.time.LocalDateTime;

public class UserData {
    private int totalPlays;
    private double averageScore;
    private Boolean isOnline;
    private String username;
    private String firstName;
    private String secondName;
    private double userOverAllScore;

    public UserData(User user,double UserOverAllScore){
        username=user.getUsername();
        firstName=user.getFirstName();
        secondName=user.getLastName();
        totalPlays=user.getPlays();
        averageScore= user.getAvgScore();
        LocalDateTime localDateTime=user.getLastRequest();
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(10);
        int result = localDateTime1.compareTo(LocalDateTime.now());
        if(result>0){
            isOnline=true;
        }else {
            isOnline=false;
        }
        this.userOverAllScore=UserOverAllScore;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Integer getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(Integer totalPlays) {
        this.totalPlays = totalPlays;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public double getUserOverAllScore() {
        return userOverAllScore;
    }

    public void setUserOverAllScore(double userOverAllScore) {
        this.userOverAllScore = userOverAllScore;
    }
}
