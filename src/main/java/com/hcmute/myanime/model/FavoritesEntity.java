package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites", schema = "movie")
public class FavoritesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private MovieSeriesEntity movieSeries; // mappedBy in table

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private UsersEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public MovieSeriesEntity getMovieSeries() {
        return movieSeries;
    }

    public void setMovieSeries(MovieSeriesEntity movieSeries) {
        this.movieSeries = movieSeries;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}