package LCK.snowTaxi2.repository;

import LCK.snowTaxi2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    Member findByNickname(String email);

}
