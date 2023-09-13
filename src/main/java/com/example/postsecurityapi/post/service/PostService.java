package com.example.postsecurityapi.post.service;

import com.example.postsecurityapi.common.dto.StringResponseDto;
import com.example.postsecurityapi.post.dto.PostRequestDto;
import com.example.postsecurityapi.post.dto.PostResponseDto;
import com.example.postsecurityapi.post.entity.Post;
import com.example.postsecurityapi.post.repository.PostRepository;
import com.example.postsecurityapi.user.entity.User;
import com.example.postsecurityapi.user.entity.UserRoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Post Service")
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto postCreate(PostRequestDto requestDto, User user) {
        Post newPost = new Post(
                requestDto,
                user.getUsername()
        );
        user.addPost(newPost);
        return new PostResponseDto(postRepository.save(newPost));
    }

    public List<PostResponseDto> postList() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto postDetail(Long id) {
        return new PostResponseDto(findById(id));
    }

    @Transactional
    public PostResponseDto postUpdate(Long id, PostRequestDto requestDto, User user) {
        Post post = findById(id);
        log.info("post:id" + post.getUser().getId());
        log.info("user:id" + user.getId());
        if (user.getRole() == UserRoleEnum.USER && !user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다");
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    public StringResponseDto postDelete(Long id, User user) {
        Post post = findById(id);
        log.info("post:id" + post.getUser().getId());
        log.info("user:id" + user.getId());
        if (user.getRole() == UserRoleEnum.USER && !user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("유효하지 않은 회원 정보입니다");
        }
        postRepository.delete(post);
        return new StringResponseDto("success", "200");
    }

    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 글입니다."));
    }
}
