package account.model.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);
    private final String name;
    private final String lastname;
    private final String password;
    private final String email;
    private final boolean isLocked;
    private Set<UserGroup> userGroups;

    public UserDetailsImpl(User user) {
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.isLocked = user.isLocked();
        this.userGroups = user.getUserGroups();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<UserGroup> userGroups = this.userGroups;
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for (UserGroup userGroup : userGroups) {
            authorities.add(new SimpleGrantedAuthority(userGroup.getCode().toUpperCase()));
        }

        logger.info("CHECK LIST OF AUTHORITIES: {}", authorities);
        logger.info("CHECK USER NAME: {}", email);
        logger.info("CHECK USER LOCKED: {}", isLocked);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
