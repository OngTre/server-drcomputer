package TranQuocToan.Java.DoAn.Repository;

import TranQuocToan.Java.DoAn.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByUid(String uid);
}