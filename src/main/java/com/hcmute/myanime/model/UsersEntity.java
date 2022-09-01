package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", schema = "movie")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String avatar;
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createAt;
    @Column(columnDefinition = "boolean default true")
    private Boolean enable;
    @OneToMany(mappedBy = "usersByUserId")
    @JsonBackReference
    private Collection<CommentEntity> commentsById;
    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private RolesEntity userRoleByUserRoleId;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Collection<LogHistoriesEntity> logHistoriesEntityCollection;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Collection<FavoritesEntity> favoritesEntityCollection;

    public UsersEntity() {
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<LogHistoriesEntity> getLogHistoriesEntityCollection() {
        return logHistoriesEntityCollection;
    }

    public void setLogHistoriesEntityCollection(Collection<LogHistoriesEntity> logHistoriesEntityCollection) {
        this.logHistoriesEntityCollection = logHistoriesEntityCollection;
    }

    public Collection<FavoritesEntity> getFavoritesEntityCollection() {
        return favoritesEntityCollection;
    }

    public void setFavoritesEntityCollection(Collection<FavoritesEntity> favoritesEntityCollection) {
        this.favoritesEntityCollection = favoritesEntityCollection;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public RolesEntity getUserRoleByUserRoleId() {
        return userRoleByUserRoleId;
    }

    public void setUserRoleByUserRoleId(RolesEntity userRoleByUserRoleId) {
        this.userRoleByUserRoleId = userRoleByUserRoleId;
    }
}
