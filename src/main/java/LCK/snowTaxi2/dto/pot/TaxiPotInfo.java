package LCK.snowTaxi2.dto.pot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TaxiPotInfo {

    private long potId;

    @JsonFormat(pattern = "a hh:mm", timezone = "Asia/Seoul")
    private LocalTime ridingTime;

}
