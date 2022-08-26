package com.hcmute.myanime.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites", schema = "movie")
public class FavoritesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private Timestamp createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private MovieSeriesEntity movieSeries; // mappedBy in table

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private UsersEntity user;
}
