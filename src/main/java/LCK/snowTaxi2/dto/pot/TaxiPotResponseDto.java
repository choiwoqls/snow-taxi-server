package LCK.snowTaxi2.dto.pot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Builder
@ToString
public class TaxiPotResponseDto implements Comparable<TaxiPotResponseDto> {

    private long id;

    @JsonFormat(pattern = "a hh:mm", timezone = "Asia/Seoul")
    private LocalTime ridingTime;

    private int headCount;

    private boolean isParticipating;

    // 탑승 시간을 기준으로 정렬해주기 위해 추가
    @Override
    public int compareTo(TaxiPotResponseDto taxiPotResponseDto) {
        return this.ridingTime.compareTo(taxiPotResponseDto.getRidingTime());
    }
}
