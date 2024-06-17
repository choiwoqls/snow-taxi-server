package LCK.snowTaxi2.service.pot;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.pot.Departure;
import LCK.snowTaxi2.dto.pot.TaxiPotInfo;
import LCK.snowTaxi2.dto.pot.TaxiPotResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public interface TaxiPotService {

    // 팟 생성하기 (컨트롤러에서 택시팟 생성하고 반환받은 id 값으로 participationService.create 실행)
    long create(String departure, LocalTime ridingTime);

    // 택시 루트별로 오늘 모집중인 팟 중 시간이 유효한 팟을 탑승시간을 기준으로 정렬해 리턴
    List<TaxiPotResponseDto> getTodayValidPots(Departure departure, Long memberId);

    // 로그인하지 않은 사용자가 조회
    List<TaxiPotResponseDto> getTodayPots(Departure departure);

    // 현재 참여중인 팟 정보 불러오기
    TaxiPotResponseDto findParticipatingPot(Member member);

}
