package LCK.snowTaxi2.jwt;

import lombok.Getter;

@Getter
public class TokenInfoVo {
    private Long memberId;
    private String nickname;

    TokenInfoVo(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
