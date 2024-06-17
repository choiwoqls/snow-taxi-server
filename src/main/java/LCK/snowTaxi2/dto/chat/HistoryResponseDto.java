package LCK.snowTaxi2.dto.chat;

import LCK.snowTaxi2.dto.pot.MyPotsResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HistoryResponseDto {

    private List<MessageResponseDto> chats;

    private MyPotsResponseDto pot;

}
