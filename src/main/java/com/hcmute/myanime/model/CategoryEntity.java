package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "category", schema = "movie")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @Basic
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;
    @OneToMany(mappedBy = "categoryByCategoryId", fetch = FetchType.LAZY)
    @JsonBackReference
    private Collection<CategoryMovieEntity> categoryMoviesById;

    public CategoryEntity() {
    }

    public CategoryEntity(String name, Timestamp createAt) {
        this.name = name;
        this.createAt = createAt;
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

        CategoryEntity that = (CategoryEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<CategoryMovieEntity> getCategoryMoviesById() {
        return categoryMoviesById;
    }

    public void setCategoryMoviesById(Collection<CategoryMovieEntity> categoryMoviesById) {
        this.categoryMoviesById = categoryMoviesById;
    }
}
