package LCK.snowTaxi2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nickname")})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    private String email;
    private String nickname;
    private String password;

    // 현재 참여중인 팟의 아이디 (참여중이지 않으면 0)
    private long participatingPotId = 0;
    // 현재 참여중인 팟 탑승 시간
    private LocalTime ridingTime;

    // 참여했던 정보
    @OneToMany(mappedBy = "member")
    private List<Participation> participations = new ArrayList<>();

}