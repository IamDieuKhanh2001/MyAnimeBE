package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "gift_codes", schema = "movie")
public class GiftCodeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcription_package_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private SubscriptionPackageEntity subscriptionPackageById;

    private String redemptionCode;
    @CreationTimestamp
    private Timestamp createAt;
}
