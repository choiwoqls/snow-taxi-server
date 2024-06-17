package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.member.LoginResponseDto;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.dto.member.MemberResponseDto;
import LCK.snowTaxi2.dto.pot.TaxiPotInfo;
import LCK.snowTaxi2.jwt.TokenInfoVo;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/signUp")
    public ResultResponse signUp(@RequestBody MemberRequestDto memberRequest) {
        if (!memberService.isMemberEmail(memberRequest.getEmail())) {
            memberService.createMember(memberRequest);
            return ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("회원가입에 성공했습니다.")
                    .build();
        }
        else {
            return ResultResponse.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .message("회원가입에 실패했습니다.")
                    .build();
        }
    }

    @PostMapping("/login")
    public ResultResponse login(@RequestBody MemberRequestDto memberRequest, HttpServletResponse response) {
        // 회원 정보 조회
        LoginResponseDto loginResponseDto = memberService.findMember(memberRequest);
        MemberResponseDto memberResponseDto = null;

        // 로그인 성공
        if (loginResponseDto.getLoginStatus() == HttpStatus.OK.value()) {
            TokenInfoVo tokenInfoVo = jwtService.setTokenInfo(memberRequest);
            String access_token = jwtService.createAccessToken(tokenInfoVo);
            jwtService.sendAccessToken(response, access_token);

            Member loginMember = loginResponseDto.getMember();

            memberResponseDto = MemberResponseDto.builder()
                    .potInfo(new TaxiPotInfo(loginMember.getParticipatingPotId(), loginMember.getRidingTime()))
                    .email(loginMember.getEmail())
                    .nickname(loginMember.getNickname())
                    .build();
        }

        return ResultResponse.builder()
                .code(loginResponseDto.getLoginStatus())
                .message(loginResponseDto.getMessage())
                .data(memberResponseDto)
                .build();
    }

    @GetMapping("/logout")
    public ResultResponse logout(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("로그아웃 합니다.")
                .build();
    }

    @PostMapping("/nicknameCheck")
    public ResultResponse nicknameCheck(@RequestParam String nickname) {
        if (!memberService.isMemberNickname(nickname)) {
            return ResultResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("사용 가능한 닉네임입니다.")
                    .build();
        }
        else {
            return ResultResponse.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .message("닉네임이 중복됩니다.")
                    .build();
        }
    }

    @PostMapping("/changePassword")
    public ResultResponse changePassword(@RequestBody MemberRequestDto memberRequest) {
        if (!memberService.isMemberEmail(memberRequest.getEmail())) {
            return ResultResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("회원이 아닙니다.")
                    .build();
        }
        memberService.changePassword(memberRequest);
        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("비밀번호가 변경되었습니다.")
                .build();
    }

}