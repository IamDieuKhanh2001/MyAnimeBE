package com.hcmute.myanime.model;

import javax.persistence.*;

@Entity
@Table(name = "category_movie", schema = "movie")
public class CategoryMovieEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity categoryByCategoryId;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false)
    private MovieEntity movieByMovieId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryMovieEntity that = (CategoryMovieEntity) o;

        if (id != that.id) return false;

        return true;
    }

    public CategoryEntity getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(CategoryEntity categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    public MovieEntity getMovieByMovieId() {
        return movieByMovieId;
    }

    public void setMovieByMovieId(MovieEntity movieByMovieId) {
        this.movieByMovieId = movieByMovieId;
    }
}
