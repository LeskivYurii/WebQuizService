package engine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<QuizCompletedInfo> quizCompletedInfos = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Quiz> quizzes = new HashSet<>();

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public List<QuizCompletedInfo> getCompletedQuizInfos() {
        return quizCompletedInfos;
    }

    public void setCompletedQuizInfos(List<QuizCompletedInfo> quizCompletedInfos) {
        this.quizCompletedInfos = quizCompletedInfos;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
