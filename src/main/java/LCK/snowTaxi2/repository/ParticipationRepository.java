package LCK.snowTaxi2.repository;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.pot.TaxiPot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    void deleteParticipationByTaxiPotAndMember(TaxiPot taxiPot, Member member);

}
