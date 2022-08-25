package com.hcmute.myanime.model;

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
    private Date publicDate;
    private String description;
    private String videoTrailer;
    private String studioName;
    private String image;
    private Timestamp dateAired;
    private Timestamp createAt;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
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

    public MovieEntity(String title, Date publicDate, String description, String videoTrailer, String studioName, String image, Timestamp dateAired) {
        this.title = title;
        this.publicDate = publicDate;
        this.description = description;
        this.videoTrailer = videoTrailer;
        this.studioName = studioName;
        this.image = image;
        this.dateAired = dateAired;
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

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoTrailer() {
        return videoTrailer;
    }

    public void setVideoTrailer(String videoTrailer) {
        this.videoTrailer = videoTrailer;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getDateAired() {
        return dateAired;
    }

    public void setDateAired(Timestamp dateAired) {
        this.dateAired = dateAired;
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
        if (publicDate != null ? !publicDate.equals(that.publicDate) : that.publicDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (videoTrailer != null ? !videoTrailer.equals(that.videoTrailer) : that.videoTrailer != null) return false;
        if (studioName != null ? !studioName.equals(that.studioName) : that.studioName != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (dateAired != null ? !dateAired.equals(that.dateAired) : that.dateAired != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publicDate != null ? publicDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (videoTrailer != null ? videoTrailer.hashCode() : 0);
        result = 31 * result + (studioName != null ? studioName.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (dateAired != null ? dateAired.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    public Collection<MovieSeriesEntity> getMovieSeriesById() {
        return movieSeriesById;
    }

    public void setMovieSeriesById(Collection<MovieSeriesEntity> movieSeriesById) {
        this.movieSeriesById = movieSeriesById;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicDate=" + publicDate +
                ", description='" + description + '\'' +
                ", videoTrailer='" + videoTrailer + '\'' +
                ", studioName='" + studioName + '\'' +
                ", image='" + image + '\'' +
                ", dateAired=" + dateAired +
                ", createAt=" + createAt +
                ", views=" + views +
                '}';
    }
}
