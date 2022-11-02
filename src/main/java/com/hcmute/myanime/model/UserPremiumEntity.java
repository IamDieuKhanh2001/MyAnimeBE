package com.hcmute.myanime.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_premiums", schema = "movie")
public class UserPremiumEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private UsersEntity usersEntityById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_package_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private SubscriptionPackageEntity subscriptionPackageBySubscriptionPackageId;

    private Timestamp subscribeDate;
    private Timestamp expiredAt;
}
