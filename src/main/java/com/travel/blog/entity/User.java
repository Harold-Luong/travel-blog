package com.travel.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String passwordHash;
    private String bio;

    // Google profile data khi login with google
    // Google unique ID (sub)
    @Column(name = "google_id", unique = true)
    private String googleId;
    private String avatarUrl;
    private String givenName;
    private String familyName;
    private String locale = "vi";

    @OneToMany(mappedBy = "user")
    private List<Trip> trips;

    @OneToMany(mappedBy = "user")
    private List<Content> contents;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    public User(Long id, String email, String givenName, String familyName, String avatar, String bio) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.avatarUrl = avatar;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "avatarUrl='" + avatarUrl + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
