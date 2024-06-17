package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.pot.Departure;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.pot.MyPotsResponseDto;
import LCK.snowTaxi2.dto.pot.TaxiPotInfo;
import LCK.snowTaxi2.dto.pot.TaxiPotRequestDto;
import LCK.snowTaxi2.dto.pot.TaxiPotResponseDto;
import LCK.snowTaxi2.jwt.JwtService;
import LCK.snowTaxi2.jwt.TokenInfoVo;
import LCK.snowTaxi2.service.member.MemberService;
import LCK.snowTaxi2.service.participation.ParticipationService;
import LCK.snowTaxi2.service.pot.TaxiPotService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pot")
public class TaxiPotController {

    private final TaxiPotService taxiPotService;
    private final ParticipationService participationService;
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/new")
    public ResultResponse create(HttpServletRequest request, @RequestBody TaxiPotRequestDto requestDto) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        boolean canCreate = (memberService.getParticipatingPotId(tokenInfoVo.getMemberId()) == 0);
        long potId = 0;

        if (canCreate) {
            potId = taxiPotService.create(requestDto.getDeparture(), requestDto.getRidingTime());
            participationService.create(tokenInfoVo.getMemberId(), potId);
        }

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("pot 생성")
                .data(potId)
                .build();
    }

    @GetMapping("/valid")
    public ResultResponse getValidPots(HttpServletRequest request, @RequestParam String departure) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        List<TaxiPotResponseDto> response =  taxiPotService.getTodayValidPots(Departure.valueOf(departure), tokenInfoVo.getMemberId());

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("탑승 가능한 pot 조회")
                .data(response)
                .build();
    }

    @GetMapping("/default")
    public ResultResponse getPots(@RequestParam String departure) {
        List<TaxiPotResponseDto> response =  taxiPotService.getTodayPots(Departure.valueOf(departure));

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("탑승 가능한 pot 조회")
                .data(response)
                .build();
    }

    @GetMapping("/my")
    public ResultResponse getHistory(HttpServletRequest request) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        List<MyPotsResponseDto> myPots = memberService.getMyPots(tokenInfoVo.getMemberId());

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("택시 팟 참여 내역 조회")
                .data(myPots)
                .build();
    }

    @GetMapping("/finish")
    public ResultResponse finishParticipation(HttpServletRequest request) {
        String access_token = jwtService.extractAccessToken(request).orElseGet(() -> "");
        TokenInfoVo tokenInfoVo = jwtService.getTokenInfo(access_token);

        memberService.finishParticipation(tokenInfoVo.getMemberId());

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("팟 참여 완료")
                .build();
    }

}
