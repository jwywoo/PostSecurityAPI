package com.example.postsecurityapi.post.controller;

import com.example.postsecurityapi.common.dto.StringResponseDto;
import com.example.postsecurityapi.common.security.UserDetailsImpl;
import com.example.postsecurityapi.post.dto.PostRequestDto;
import com.example.postsecurityapi.post.dto.PostResponseDto;
import com.example.postsecurityapi.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j(topic = "Post controller")
public class PostController {
    private final PostService postService;

    // Post Create
    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> postCreate(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("post create");
        return ResponseEntity.ok(postService.postCreate(requestDto, userDetails.getUser()));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> postList() {
        return ResponseEntity.ok(postService.postList());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> postDetail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.postDetail(id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> postUpdate(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.postUpdate(id, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<StringResponseDto> postUpdate(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.postDelete(id, userDetails.getUser()));
    }
}
