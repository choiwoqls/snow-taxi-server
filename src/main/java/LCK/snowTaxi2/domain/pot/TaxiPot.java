package LCK.snowTaxi2.domain.pot;

import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.chat.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taxi_pot")
public class TaxiPot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taxi_pot_id")
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private Departure departure;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ridingDate;

    @JsonFormat(pattern = "a hh:mm", timezone = "Asia/Seoul")
    private LocalTime ridingTime;

    private int headCount;

    @OneToMany(mappedBy = "taxiPot")
    private List<Participation> participations = new ArrayList<>();

    @OneToMany(mappedBy = "taxiPot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> chatMessages = new ArrayList<>();

}
