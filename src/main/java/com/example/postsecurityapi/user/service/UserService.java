package com.example.postsecurityapi.user.service;


import com.example.postsecurityapi.common.dto.StringResponseDto;
import com.example.postsecurityapi.user.dto.SignupRequestDto;
import com.example.postsecurityapi.user.entity.User;
import com.example.postsecurityapi.user.entity.UserRoleEnum;
import com.example.postsecurityapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    @Transactional
    public StringResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> signMember = userRepository.findByUsername(username);
        if (signMember.isPresent()){
            return new StringResponseDto("아이디가 중복됩니다", "400");
        }

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, roleEnum);
        userRepository.save(user);
        return new StringResponseDto("회원가입을 축하합니다", "200");
    }
}