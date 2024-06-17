package LCK.snowTaxi2.jwt;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("asdasdasdasd")
    private String secretKey;

    @Value("14124124124124")
    private Long accessTokenExpirationPeriod;

    @Value("asdasdfs")
    private String accessHeader;


    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String MemberID_CLAIM = "memberId";
    private static final String NICKNAME_CLAIM = "nickname";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;

    public String createAccessToken(TokenInfoVo tokenInfoVo) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(MemberID_CLAIM, tokenInfoVo.getMemberId())
                .withClaim(NICKNAME_CLAIM, tokenInfoVo.getNickname())
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        log.info("Access Token 헤더 설정 완료");
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        Optional<String> access_token =
                Optional.ofNullable(request.getHeader(accessHeader))
                        .filter(accessToken -> accessToken.startsWith(BEARER))
                        .map(accessToken -> accessToken.replace(BEARER, ""));
        System.out.println(access_token);
        return access_token;
    }


    public TokenInfoVo getTokenInfo(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);

        Long memberId = decodedJWT.getClaim(MemberID_CLAIM).asLong();
        String nickname = decodedJWT.getClaim(NICKNAME_CLAIM).asString();
        return new TokenInfoVo(memberId, nickname);
    }

    public TokenInfoVo setTokenInfo(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findByEmail(memberRequestDto.getEmail());
        return new TokenInfoVo(member.getId(), member.getNickname());
    }

    public Optional<Long> extractInfo(String accessToken) {
        try {
            log.info("액세스 토큰이 유효해서 정보를 가져옵니다.");
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(MemberID_CLAIM)
                    .asLong());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public boolean isTokenValid(String token) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier 빌드하고 verify(token)
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            log.info("유효한 토큰입니다");
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }
}
