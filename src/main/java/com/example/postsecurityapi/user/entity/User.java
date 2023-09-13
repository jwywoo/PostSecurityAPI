package com.example.postsecurityapi.user.entity;

import com.example.postsecurityapi.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Post> postList = new ArrayList<>();

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void addPost(Post newPost) {
        this.postList.add(newPost);
        newPost.setUser(this);
    }
}
