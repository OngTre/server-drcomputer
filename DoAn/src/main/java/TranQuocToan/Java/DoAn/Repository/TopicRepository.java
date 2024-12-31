package TranQuocToan.Java.DoAn.Repository;

import TranQuocToan.Java.DoAn.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TopicRepository extends JpaRepository<Topic, Long> {
    // Kiểm tra xem Topic đã tồn tại chưa dựa trên tên
    boolean existsByName(String name);
}