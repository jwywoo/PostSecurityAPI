package com.example.postsecurityapi.post.entity;

import com.example.postsecurityapi.common.entity.Timestamped;
import com.example.postsecurityapi.post.dto.PostRequestDto;
import com.example.postsecurityapi.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="post")
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Post(PostRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
