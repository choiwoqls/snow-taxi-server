package LCK.snowTaxi2.dto.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequestDto {

    private long roomId;

    private String sender;

    private String content;

    private String type;

}
