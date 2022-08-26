package com.hcmute.myanime.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "movies", schema = "movie")
public class MovieEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String title;
    private String studioName;
    private Timestamp createAt;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "category_movie", // table link two relationship
            joinColumns = @JoinColumn(name = "movie_id"), // Key is link with table Users
            inverseJoinColumns = @JoinColumn(name = "category_id")) //Key is link with table Roles
    private Collection<CategoryEntity> categoryEntityCollection;
    @OneToMany(mappedBy = "movieByMovieId")
    private Collection<MovieSeriesEntity> movieSeriesById;

    public Collection<CategoryEntity> getCategoryEntityCollection() {
        return categoryEntityCollection;
    }

    public void setCategoryEntityCollection(Collection<CategoryEntity> categoryEntityCollection) {
        this.categoryEntityCollection = categoryEntityCollection;
    }

    @Transient
    private Integer views = 5;

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public MovieEntity() {
    }

    public MovieEntity(String title, String studioName) {
        this.title = title;
        this.studioName = studioName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
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

        MovieEntity that = (MovieEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (studioName != null ? !studioName.equals(that.studioName) : that.studioName != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (studioName != null ? studioName.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<MovieSeriesEntity> getMovieSeriesById() {
        return movieSeriesById;
    }

    public void setMovieSeriesById(Collection<MovieSeriesEntity> movieSeriesById) {
        this.movieSeriesById = movieSeriesById;
    }
}
