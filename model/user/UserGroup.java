package account.model.user;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "principle_groups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String code;

    @ManyToMany(mappedBy = "userGroups")
    private Set<User> users;

    public UserGroup() {
    }

    public UserGroup(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
