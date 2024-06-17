package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.chat.HistoryResponseDto;
import LCK.snowTaxi2.dto.chat.MessageRequestDto;
import LCK.snowTaxi2.dto.chat.MessageResponseDto;
import LCK.snowTaxi2.service.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat")
    public void send(MessageRequestDto messageRequestDto) {
        messageService.send(messageRequestDto);
    }

    @GetMapping("/chatroom")
    public ResultResponse getChats(@RequestParam long roomId) {
        List<MessageResponseDto> responseDto = messageService.getChats(roomId);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("채팅 메시지 조회")
                .data(responseDto)
                .build();

    }

    @GetMapping("/history")
    public ResultResponse getHistoryDetail(@RequestParam long roomId) {
        HistoryResponseDto responseDto = messageService.getHistoryDetail(roomId);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("전에 참여했던 팟의 채팅 메시지 조회")
                .data(responseDto)
                .build();

    }

    @PostMapping("/chatroom/inout")
    public void enterChatRoom(@RequestBody MessageRequestDto messageRequestDto) { messageService.send(messageRequestDto); }

}
