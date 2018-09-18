package pl.coderslab.bettingsite.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private final pl.coderslab.bettingsite.entity.User user;

    public CurrentUser(String email,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.bettingsite.entity.User user) {
        super(email, password, authorities);
        this.user = user;
    }

    public pl.coderslab.bettingsite.entity.User getUser() {
        return this.user;
    }
}