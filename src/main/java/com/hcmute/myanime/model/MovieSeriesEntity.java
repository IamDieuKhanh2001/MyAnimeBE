package com.hcmute.myanime.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "movie_series", schema = "movie")
public class MovieSeriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;
    @OneToMany(mappedBy = "movieSeriesBySeriesId")
    private Collection<EpisodeEntity> episodesById;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieEntity movieByMovieId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        MovieSeriesEntity that = (MovieSeriesEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<EpisodeEntity> getEpisodesById() {
        return episodesById;
    }

    public void setEpisodesById(Collection<EpisodeEntity> episodesById) {
        this.episodesById = episodesById;
    }

    public MovieEntity getMovieByMovieId() {
        return movieByMovieId;
    }

    public void setMovieByMovieId(MovieEntity movieByMovieId) {
        this.movieByMovieId = movieByMovieId;
    }
}
