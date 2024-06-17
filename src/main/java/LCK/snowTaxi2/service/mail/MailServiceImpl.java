//package LCK.snowTaxi2.service.mail;
//
//import LCK.snowTaxi2.domain.Member;
//import LCK.snowTaxi2.repository.MemberRepository;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MailServiceImpl implements MailService{
//
//    private final JavaMailSender javaMailSender;
//    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private static final String senderEmail= "0923ule@gmail.com";
//
//    public int createNumber() {
//        // (int) Math.random() * (최댓값-최소값+1) + 최소값
//        int number = (int)(Math.random() * (90000)) + 100000;
//        return number;
//    }
//
//    public String createTempPassword() {
//        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
//                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
//
//        String str = "";
//
//        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
//        int idx = 0;
//        for (int i = 0; i < 10; i++) {
//            idx = (int) (charSet.length * Math.random());
//            str += charSet[idx];
//        }
//        return str;
//    }
//
//    public int sendAuthMail(String mail) {
//        int number = createNumber();
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        try {
//            message.setFrom(senderEmail);
//            message.setRecipients(MimeMessage.RecipientType.TO, mail);
//            message.setSubject("[SNOWTAXI] 이메일 인증");
//            String body = "";
//            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
//            body += "<h1>" + number + "</h1>";
//            body += "<h3>" + "감사합니다." + "</h3>";
//            message.setText(body,"UTF-8", "html");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        javaMailSender.send(message);
//        return number;
//    }
//
//    public void sendPasswordMail(String mail) {
//        String tmpPassword = createTempPassword();
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        try {
//            Member member = memberRepository.findByEmail(mail);
//            member.setPassword(bCryptPasswordEncoder.encode(tmpPassword));
//            memberRepository.saveAndFlush(member);
//
//            message.setFrom(senderEmail);
//            message.setRecipients(MimeMessage.RecipientType.TO, mail);
//            message.setSubject("[SNOWTAXI] 비밀번호 재설정");
//            String body = "";
//            body += "<h3>" + "임시 비밀 번호입니다." + "</h3>";
//            body += "<h1>" + tmpPassword + "</h1>";
//            body += "<h3>" + "로그인 후 비밀번호를 변경하시기 바랍니다." + "</h3>";
//            message.setText(body,"UTF-8", "html");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        javaMailSender.send(message);
//    }
//
//}
