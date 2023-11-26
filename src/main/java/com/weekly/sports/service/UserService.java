package com.weekly.sports.service;

import static com.weekly.sports.common.meta.ResultCode.SYSTEM_ERROR;

import com.fasterxml.jackson.databind.JsonNode;
import com.weekly.sports.common.exception.GlobalException;
import com.weekly.sports.common.jwt.JwtUtil;
import com.weekly.sports.common.validator.UserValidator;
import com.weekly.sports.model.dto.request.FollowReq;
import com.weekly.sports.model.dto.request.UserProfileReq;
import com.weekly.sports.model.dto.request.UserSignUpDto;
import com.weekly.sports.model.dto.request.UserUpdateReq;
import com.weekly.sports.model.dto.response.FollowRes;
import com.weekly.sports.model.dto.response.UserProfileRes;
import com.weekly.sports.model.dto.response.UserUpdateRes;
import com.weekly.sports.model.entity.FollowEntity;
import com.weekly.sports.model.entity.UserEntity;
import com.weekly.sports.model.entity.UserSocialEnum;
import com.weekly.sports.repository.FollowRepository;
import com.weekly.sports.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();
    private final FollowRepository followRepository;


    public void signUp(UserSignUpDto userSignUpDto) {
        List<UserEntity> prevUserList = userRepository.findByEmail(userSignUpDto.getEmail());
        if (!CollectionUtils.isEmpty(prevUserList)) {
            throw new GlobalException(SYSTEM_ERROR);
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

    public String socialLogin(String code, String registrationId, HttpServletResponse response) {
        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        String email = userResourceNode.get("email").asText();

        if (userRepository.findByEmail(email).isEmpty()) {
            UserEntity user = UserEntity.builder()
                .email(email)
                .social(UserSocialEnum.GOOGLE)
                .build();
            log.info(user.getEmail());
            userRepository.save(user);
            String token = jwtUtil.createToken(email);
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
            return "회원가입 성공 입니다.";
        }
        String token = jwtUtil.createToken(email);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return "로그인 성공 입니다.";
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty(
            "spring.security.oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty(
            "spring.security.oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty(
            "spring.security.oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty(
            "spring.security.oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST,
            entity,
            JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty(
            "spring.security.oauth2." + registrationId + ".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    public UserProfileRes getUserProfile(UserProfileReq userProfileReq) {
        UserEntity userEntity = userRepository.findByUserId(userProfileReq.getUserId());
        UserValidator.validator(userEntity);
        return UserServiceMapper.INSTANCE.toUserProfileRes(userEntity);
    }

    public UserUpdateRes updateUser(UserUpdateReq userUpdateReq) {
        UserEntity prevUser = userRepository.findByUserId(userUpdateReq.getUserId());
        UserValidator.validator(prevUser);
        userRepository.save(UserEntity.builder()
            .userId(prevUser.getUserId())
            .email(prevUser.getEmail())
            .username(userUpdateReq.getUsername())
            .password(passwordEncoder.encode(userUpdateReq.getPassword()))
            .introduction(userUpdateReq.getIntroduction())
            .social(prevUser.getSocial())
            .build());
        return new UserUpdateRes();
    }

    public FollowRes followUser(FollowReq followReq) {
        UserEntity followerUser = getUserEntityByUserId(followReq.getFollowerUserId());
        UserEntity followingUser = getUserEntityByUserId(followReq.getFollowingUserId());

        FollowEntity followEntity = getPrevFollow(followerUser, followingUser);
        UserValidator.checkAlreadyFollowed(followEntity);

        followRepository.save(FollowEntity.builder()
            .followerUserId(followerUser)
            .followingUserId(followingUser)
            .build());
        return new FollowRes();
    }

    public FollowRes unFollowUser(FollowReq followReq) {
        UserEntity followerUser = getUserEntityByUserId(followReq.getFollowerUserId());
        UserEntity followingUser = getUserEntityByUserId(followReq.getFollowingUserId());

        FollowEntity followEntity = getPrevFollow(followerUser, followingUser);
        UserValidator.checkNotYetFollowed(followEntity);

        followRepository.delete(followEntity);
        return new FollowRes();
    }

    private UserEntity getUserEntityByUserId(Long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        UserValidator.validator(user);
        return user;
    }

    private FollowEntity getPrevFollow(UserEntity followerUser, UserEntity followingUser) {
        return followRepository.findByFollowerUserIdAndFollowingUserId(followerUser, followingUser);
    }

    @Mapper
    public interface UserServiceMapper {

        UserServiceMapper INSTANCE = Mappers.getMapper(UserServiceMapper.class);

        default int toFollowerCnt(List<FollowEntity> followEntities) {
            return followEntities.size();
        }

        @Mapping(source = "followEntities", target = "followerCnt")
        UserProfileRes toUserProfileRes(UserEntity userEntity);
    }
}
