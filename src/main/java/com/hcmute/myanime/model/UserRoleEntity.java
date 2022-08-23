package com.hcmute.myanime.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "user_role", schema = "movie")
public class UserRoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "permission", nullable = false, length = 255)
    private String permission;
    @Basic
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;
    @OneToMany(mappedBy = "userRoleByUserRoleId")
    private Collection<UsersEntity> usersById;

    public UserRoleEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleEntity that = (UserRoleEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (permission != null ? !permission.equals(that.permission) : that.permission != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<UsersEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UsersEntity> usersById) {
        this.usersById = usersById;
    }
}
