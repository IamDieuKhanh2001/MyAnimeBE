package com.hcmute.myanime.model;

import javax.persistence.*;

@Entity
@Table(name = "category_movie", schema = "movie")
public class CategoryMovieEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
}
