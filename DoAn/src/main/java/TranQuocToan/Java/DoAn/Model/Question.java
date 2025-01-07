package TranQuocToan.Java.DoAn.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotBlank
    private String subject = "Default Subject";

    @NotBlank
    private String questionType = "single";

    // Định nghĩa bảng phụ cho các lựa chọn
    @ElementCollection
    @CollectionTable(
        name = "question_choices",
        joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "choice", nullable = false)
    private List<String> choices;

    // Định nghĩa bảng phụ cho các câu trả lời đúng
    @ElementCollection
    @CollectionTable(
        name = "question_correct_answers",
        joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "correct_answer", nullable = false)
    private List<String> correctAnswers;

    // Thêm các getter và setter khác nếu cần
}

// package TranQuocToan.Java.DoAn.Model;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import lombok.Data;
// import lombok.Getter;
// import lombok.Setter;


// import java.util.List;

// @Getter
// @Setter
// @Data
// @Entity
// public class Question {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @NotBlank(message = "Question cannot be blank")
//     private String question;

//     @NotBlank
//     private String subject = "Default Subject";

//     @NotBlank
//     private String questionType = "single";a

//     // Định nghĩa bảng phụ cho các lựa chọn
//     @ElementCollection
//     @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
//     @Column(name = "choice")
//     private List<String> choices;

//     // Định nghĩa bảng phụ cho các câu trả lời đúng
//     @ElementCollection
//     @CollectionTable(name = "question_correct_answers", joinColumns = @JoinColumn(name = "question_id"))
//     @Column(name = "correct_answer")
//     private List<String> correctAnswers;

//     // Getter và Setter cho 'choices'
//     public List<String> getChoices() {
//         return choices;
//     }

//     public void setChoices(List<String> choices) {
//         this.choices = choices;
//     }

//     // Getter và Setter cho 'correctAnswers'
//     public List<String> getCorrectAnswers() {
//         return correctAnswers;
//     }

//     public void setCorrectAnswers(List<String> correctAnswers) {
//         this.correctAnswers = correctAnswers;
//     }

//     // Getter và Setter cho 'question'
//     public String getQuestionText() {
//         return question;
//     }

//     public void setQuestionText(String question) {
//         this.question = question;
//     }

//     // Getter và Setter cho 'subject'
//     public String getSubject() {
//         return subject;
//     }

//     public void setSubject(String subject) {
//         this.subject = subject;
//     }

//     // Getter và Setter cho 'questionType'
//     public String getQuestionType() {
//         return questionType;
//     }

//     public void setQuestionType(String questionType) {
//         this.questionType = questionType;
//     }
// }
