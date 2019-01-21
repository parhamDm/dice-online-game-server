package ir.game.models.beans;

import ir.game.models.User;

import java.time.LocalDateTime;

public class UserSlider {
    private String username;
    private boolean isOnline;
    private Boolean isFriend;

    public UserSlider(User u,Boolean isFriend) {

        this.username = u.getUsername();
        LocalDateTime localDateTime=u.getLastRequest();
        LocalDateTime localDateTime1 = localDateTime.plusMinutes(10);
        int result = localDateTime1.compareTo(LocalDateTime.now());
        if(result>0){
            isOnline=true;
        }else {
            isOnline=false;
        }
        this.isFriend=isFriend;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        this.isOnline = online;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }
}
