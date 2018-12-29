package ir.game.models.beans;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_CLIENT,ROLE_GUEST;

    public String getAuthority() {
        return name();
    }
}
