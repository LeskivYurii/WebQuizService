package engine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizCompletedInfo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;

    @JsonIgnore
    @ManyToOne
    private Quiz quiz;
    @ManyToOne
    @JsonIgnore
    private User user;

    public QuizCompletedInfo(Quiz quiz, User user) {
        completedAt = LocalDateTime.now();
        this.quiz = quiz;
        this.user = user;
    }

    public QuizCompletedInfo(int id) {
        completedAt = LocalDateTime.now();
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuizCompletedInfo() {
        completedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("id")
    public int getQuizId() {
        return quiz.getId();
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
