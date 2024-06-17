package LCK.snowTaxi2.domain.chat;

import LCK.snowTaxi2.domain.pot.TaxiPot;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_message")
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_pot_id")
    private TaxiPot taxiPot;

    private String sender;

    private String content;

    @JsonFormat(pattern = "a hh:mm", timezone = "Asia/Seoul")
    private LocalTime sentTime;

}
