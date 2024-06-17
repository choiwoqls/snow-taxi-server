package LCK.snowTaxi2.dto.member;

import LCK.snowTaxi2.dto.pot.TaxiPotInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    private TaxiPotInfo potInfo;

    private String email;

    private String nickname;

}
