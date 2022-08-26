package com.hcmute.myanime.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "episodes", schema = "movie")
public class EpisodeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String title;
    private String resource;
//    @Column(name = "views", nullable = true, length = -1)
//    private Integer views;
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createAt;
    @OneToMany(mappedBy = "episodeByEpisodeId")
    private Collection<CommentEntity> commentsById;

    @ManyToOne
    @JoinColumn(name = "series_id", referencedColumnName = "id")
    private MovieSeriesEntity movieSeriesBySeriesId;

    @OneToOne(mappedBy = "episodeEntity")
    private LogHistoriesEntity logHistoriesEntity;

    public EpisodeEntity() {
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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

        EpisodeEntity that = (EpisodeEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public MovieSeriesEntity getMovieSeriesBySeriesId() {
        return movieSeriesBySeriesId;
    }

    public void setMovieSeriesBySeriesId(MovieSeriesEntity movieSeriesBySeriesId) {
        this.movieSeriesBySeriesId = movieSeriesBySeriesId;
    }
}
