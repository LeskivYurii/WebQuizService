package engine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "Title can't be blank!")
    private String title;
    @NotBlank(message = "Text can't be blank!")
    private String text;
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();
    @Size(min = 2, message = "Size must more or equal two")
    @NotNull(message = "Options can't be null")
    @ElementCollection
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> options = new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizCompletedInfo> userCompletedInfo = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(String title, String text, List<Integer> answer, List<String> options) {
        this.title = title;
        this.text = text;
        this.answer = answer;
        this.options = options;
    }

    public boolean isCorrectAnswer(List<Integer>  answer) {
        return this.answer.size() == answer.size() && this.answer.containsAll(answer) && answer.containsAll(this.answer);
    }

    public List<QuizCompletedInfo> getUserCompletedInfo() {
        return userCompletedInfo;
    }

    public void setUserCompletedInfo(List<QuizCompletedInfo> userCompletedInfo) {
        this.userCompletedInfo = userCompletedInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer>  answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (!title.equals(quiz.title)) return false;
        if (!text.equals(quiz.text)) return false;
        if (!answer.equals(quiz.answer)) return false;
        return options.equals(quiz.options);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + answer.hashCode();
        result = 31 * result + options.hashCode();
        return result;
    }
}
