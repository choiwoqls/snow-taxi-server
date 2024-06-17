package LCK.snowTaxi2.service.participation;
import org.springframework.stereotype.Service;

@Service
public interface ParticipationService {

    // 팟에 참여하기 (headCount + 1)
    boolean create(Long memberId, Long taxiPotId);

    // 현재 참여중인 팟 나가기 (방장이 나간 경우 그 다음에 입장한 사람이 방장이 되도록, headCount - 1)
    long delete(Long memberId);

}
