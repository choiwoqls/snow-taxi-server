package LCK.snowTaxi2.dto.chat;

import LCK.snowTaxi2.domain.chat.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class MessageResponseDto {

    @JsonFormat(pattern = "a hh:mm", timezone = "Asia/Seoul")
    private LocalTime sentTime;

    private String sender;

    private String content;

    private MessageType type;

}
