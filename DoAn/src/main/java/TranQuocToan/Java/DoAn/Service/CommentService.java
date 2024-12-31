package TranQuocToan.Java.DoAn.Service;

import TranQuocToan.Java.DoAn.Repository.ICommentRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;


@Service
public class CommentService {

    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
