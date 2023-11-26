package com.weekly.sports.ryanJang.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;
    public UserDetails getUserDetails(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));
        // UserDetails는 인터페이스라 안됨
        // User 써도 되나 user 객체와 매핑이 안됨. 따라서 구현체 만들어주면 된다.
        return new UserDetailsImpl(user); //org.springframework.security.core.userdetails.User(user) 대신 생성자 UserDetailsImpl 편하게 사용
    }
}
