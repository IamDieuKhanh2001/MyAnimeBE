package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String resourcePublicId;
    @Column(columnDefinition = "Bigint default '0'")
    private Long totalView = Long.valueOf(0);
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createAt;
    @Column(columnDefinition = "boolean default true")
    private Boolean enable = true;
    @OneToMany(mappedBy = "episodeByEpisodeId")
    @JsonManagedReference
    private Collection<CommentEntity> commentsById;

    @ManyToOne
    @JoinColumn(name = "series_id", referencedColumnName = "id")
    @JsonBackReference
    private MovieSeriesEntity movieSeriesBySeriesId;

    @OneToOne(mappedBy = "episodeEntity")
    @JsonManagedReference
    private LogHistoriesEntity logHistoriesEntity;

    public EpisodeEntity() {
    }

    public Long getTotalView() {
        return totalView;
    }

    public void setTotalView(Long totalView) {
        this.totalView = totalView;
    }

    public String getResourcePublicId() {
        return resourcePublicId;
    }

    public void setResourcePublicId(String resourcePublicId) {
        this.resourcePublicId = resourcePublicId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public LogHistoriesEntity getLogHistoriesEntity() {
        return logHistoriesEntity;
    }

    public void setLogHistoriesEntity(LogHistoriesEntity logHistoriesEntity) {
        this.logHistoriesEntity = logHistoriesEntity;
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
    public String toString() {
        return "EpisodeEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", resource='" + resource + '\'' +
                ", createAt=" + createAt +
                '}';
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
