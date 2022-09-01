package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log_histories", schema = "movie")
public class LogHistoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private Long lastSecond;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private Timestamp createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
    @JsonBackReference
    private EpisodeEntity episodeEntity; // mappedBy in table

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private UsersEntity user;
}