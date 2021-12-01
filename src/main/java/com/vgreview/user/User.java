package com.vgreview.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vgreview.rating.Rating;
import com.vgreview.review.Review;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "profile_picture")
    private String profilePicture;
    private String username;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Review> userReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Rating> userRatings;

    @ManyToMany(targetEntity = Authority.class, mappedBy = "users")
    public Set<Authority> authorities;
}
