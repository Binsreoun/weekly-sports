package com.weekly.sports.common.validator;

import static com.weekly.sports.common.meta.ResultCode.EXPIRED_JWT_TOKEN;
import static com.weekly.sports.common.meta.ResultCode.INVALID_JWT_SIGNATURE;
import static com.weekly.sports.common.meta.ResultCode.JWT_CLAIMS_IS_EMPTY;
import static com.weekly.sports.common.meta.ResultCode.UNSUPPORTED_JWT_TOKEN;

import com.weekly.sports.common.exception.GlobalException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenValidator {

    public boolean validator(String token, Key key) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new GlobalException(INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new GlobalException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new GlobalException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new GlobalException(JWT_CLAIMS_IS_EMPTY);
        }
    }
}
