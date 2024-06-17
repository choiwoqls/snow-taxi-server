package LCK.snowTaxi2.dto.pot;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TaxiPotRequestDto {

    private String departure;

    private LocalTime ridingTime;

}
