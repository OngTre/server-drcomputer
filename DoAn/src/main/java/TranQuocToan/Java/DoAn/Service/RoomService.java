//package TranQuocToan.Java.DoAn.Service;
//
//import TranQuocToan.Java.DoAn.Model.Room;
//import TranQuocToan.Java.DoAn.Repository.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    public Room addUser(String uid, String displayName) {
//        Room user = new Room();
//        user.setUid(uid);
//        user.setDisplayName(displayName);
//        return roomRepository.save(user);
//    }
//
//    public List<Room> getAllUsers() {
//        return roomRepository.findAll();
//    }
//}