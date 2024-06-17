package LCK.snowTaxi2.service.member;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.dto.member.LoginResponseDto;
import LCK.snowTaxi2.dto.member.MemberRequestDto;
import LCK.snowTaxi2.dto.pot.MyPotsResponseDto;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createMember(MemberRequestDto dto) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        memberRepository.save(member);
    }

    @Override
    public long getParticipatingPotId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );
        return member.getParticipatingPotId();
    }

    @Override
    public LoginResponseDto findMember(MemberRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail());
        String message;
        int status;

        if (member == null) {
            status = HttpStatus.NOT_FOUND.value();
            message = "해당 이메일의 회원이 존재하지 않습니다.";
        } else if (!bCryptPasswordEncoder.matches(dto.getPassword(), member.getPassword())) {
            status = HttpStatus.CONFLICT.value();
            message = "비밀번호가 일치하지 않습니다.";
        } else {
            status = HttpStatus.OK.value();
            message = "로그인 성공하였습니다.";
        }

        return LoginResponseDto.builder()
                .loginStatus(status)
                .message(message)
                .member(member)
                .build();
    }

    @Override
    public boolean isMemberNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        return !(member == null);
    }

    @Override
    public boolean isMemberEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return !(member == null);
    }

    @Override
    public void changePassword(MemberRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail());
        member.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        memberRepository.saveAndFlush(member);
    }

    @Override
    public List<MyPotsResponseDto> getMyPots(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );

        List<MyPotsResponseDto> myPots = new ArrayList<>();

        for (Participation participation: member.getParticipations()){
            TaxiPot taxiPot = participation.getTaxiPot();
            if (member.getParticipatingPotId() != taxiPot.getId()) {
                myPots.add(MyPotsResponseDto.builder()
                        .id(taxiPot.getId())
                        .headCount(taxiPot.getHeadCount())
                        .ridingTime(taxiPot.getRidingTime())
                        .ridingDate(taxiPot.getRidingDate())
                        .build()
                );
            }
        }

        return myPots;
    }

    @Override
    public void finishParticipation(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member Id:", memberId.toString())
        );

        member.setParticipatingPotId(0);
        member.setRidingTime(null);
        memberRepository.saveAndFlush(member);
    }

}