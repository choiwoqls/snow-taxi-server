package LCK.snowTaxi2.repository;

import LCK.snowTaxi2.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
