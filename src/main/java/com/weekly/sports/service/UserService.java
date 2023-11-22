package com.weekly.sports.service;

import com.weekly.sports.model.dto.request.UserSignUpDto;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.model.entity.UserSocialEnum;
import com.weekly.sports.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void signUp(UserSignUpDto userSignUpDto){

    if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    UserEntity user = UserEntity.builder()
        .email(userSignUpDto.getEmail())
        .username(userSignUpDto.getUsername())
        .password(passwordEncoder.encode(userSignUpDto.getPassword()))
        .social(UserSocialEnum.LOCAL)
        .build();
    log.info(user.getEmail());
    userRepository.save(user);
  }
}
