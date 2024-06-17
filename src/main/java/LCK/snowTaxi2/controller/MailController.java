//package LCK.snowTaxi2.controller;
//
//import LCK.snowTaxi2.dto.ResultResponse;
//import LCK.snowTaxi2.service.mail.MailService;
//import LCK.snowTaxi2.service.member.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/email")
//public class MailController {
//    private final MailService mailService;
//    private final MemberService memberService;
//
//    @GetMapping("/auth")
//    public ResultResponse AuthMailSend(@RequestParam String mail) {
//
//        if (memberService.isMemberEmail(mail)) {
//            return ResultResponse.builder()
//                    .code(HttpStatus.CONFLICT.value())
//                    .message("해당 이메일의 회원이 존재합니다.")
//                    .build();
//        }
//
//        int number = mailService.sendAuthMail(mail);
//        String num = String.valueOf(number);
//        return ResultResponse.builder()
//                .code(HttpStatus.OK.value())
//                .message("인증 번호를 메일로 보냈습니다.")
//                .data(num)
//                .build();
//    }
//
//    @GetMapping("/password")
//    public ResultResponse PasswordMailSend (@RequestParam String mail){
//
//        if (!memberService.isMemberEmail(mail)) {
//            return ResultResponse.builder()
//                    .code(HttpStatus.NOT_FOUND.value())
//                    .message("해당 이메일의 회원이 존재하지 않습니다.")
//                    .build();
//        }
//
//        mailService.sendPasswordMail(mail);
//
//        return ResultResponse.builder()
//                .code(HttpStatus.OK.value())
//                .message("변경된 비밀 번호를 메일로 보냈습니다.")
//                .build();
//    }
//
//}
