package account.model.user;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserResponse {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private Set<String> roles;

    public UserResponse(long id, String name, String lastname, String email, Set<UserGroup> userGroups) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.roles = userGroups.stream()
                .map(UserGroup::getCode)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
