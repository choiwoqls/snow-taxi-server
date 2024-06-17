package LCK.snowTaxi2.service.participation;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import LCK.snowTaxi2.exception.NotFoundEntityException;
import LCK.snowTaxi2.repository.MemberRepository;
import LCK.snowTaxi2.repository.ParticipationRepository;
import LCK.snowTaxi2.repository.TaxiPotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;
    private final TaxiPotRepository taxiPotRepository;

    @Override
    @Transactional
    public boolean create(Long memberId, Long taxiPotId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member id:", memberId.toString())
        );

        if (member.getParticipatingPotId() == 0) {
            TaxiPot taxiPot = taxiPotRepository.findById(taxiPotId).orElseThrow( () ->
                    new NotFoundEntityException("taxiPot id:", taxiPotId.toString())
            );

            Participation participation = Participation.builder()
                    .member(member)
                    .taxiPot(taxiPot)
                    .build();

            participationRepository.save(participation);

            member.setParticipatingPotId(taxiPotId);
            member.setRidingTime(taxiPot.getRidingTime());
            memberRepository.saveAndFlush(member);

            taxiPot.setHeadCount(taxiPot.getHeadCount() + 1);
            taxiPotRepository.saveAndFlush(taxiPot);

            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public long delete(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow( () ->
                new NotFoundEntityException("member id:", memberId.toString())
        );

        Long taxiPotId = member.getParticipatingPotId();

        TaxiPot taxiPot = taxiPotRepository.findById(taxiPotId).orElseThrow( () ->
                new NotFoundEntityException("taxiPot id:", taxiPotId.toString())
        );

        participationRepository.deleteParticipationByTaxiPotAndMember(taxiPot, member);

        member.setParticipatingPotId(0);
        member.setRidingTime(null);
        memberRepository.saveAndFlush(member);

        taxiPot.setHeadCount(taxiPot.getHeadCount() - 1);
        taxiPotRepository.saveAndFlush(taxiPot);

        if (taxiPot.getParticipations().size() == 0) {
            taxiPotRepository.deleteById(taxiPotId);
            return 0;
        }

        return taxiPotId;
    }
}
