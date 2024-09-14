package engine.service;

import engine.domain.QuizCompletedInfo;
import engine.domain.Quiz;
import engine.domain.QuizResponse;
import engine.domain.UserDetailsAdapter;
import engine.exception.DeleteQuizException;
import engine.exception.QuizNotFoundException;
import engine.repository.QuizCompletedInfoRepository;
import engine.repository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    public static final int PAGE_SIZE = 10;

    private QuizCompletedInfoRepository quizCompletedInfoRepository;
    private QuizRepository quizRepository;


    public QuizService(QuizCompletedInfoRepository quizCompletedInfoRepository, QuizRepository quizRepository) {
        this.quizCompletedInfoRepository = quizCompletedInfoRepository;
        this.quizRepository = quizRepository;
    }

    public Quiz findById(final int id) {
        return Optional
                .of(id)
                .flatMap(quizRepository::findById)
                .orElseThrow(() -> new QuizNotFoundException("Quiz wasn't found!"));
    }

    public Page<Quiz> findAll(final int page) {
        return quizRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    public Quiz create(UserDetailsAdapter userDetailsAdapter, final Quiz quiz) {
        return Optional
                .of(quiz)
                .map(quiz1 -> {
                    quiz1.setUser(userDetailsAdapter.getUser());
                    return quizRepository.save(quiz1);
                })
                .orElse(null);
    }

    public Page<QuizCompletedInfo> findCompletedAll(UserDetailsAdapter userDetailsAdapter, final int page) {
        return quizCompletedInfoRepository.findAllByUser(userDetailsAdapter.getUser(),
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "completedAt")));
    }

    public QuizResponse solveQuiz(UserDetailsAdapter userDetailsAdapter, final int id, final List<Integer> answer) {
        Quiz quiz = Optional
                .of(id)
                .flatMap(quizRepository::findById)
                .orElseThrow(() -> new QuizNotFoundException("Quiz wasn't found!"));

        if (quiz.isCorrectAnswer(answer)) {
            QuizCompletedInfo quizCompletedInfo = new QuizCompletedInfo(quiz, userDetailsAdapter.getUser());
            quizCompletedInfoRepository.save(quizCompletedInfo);

            return new QuizResponse(true, "Congratulations, you're right!");
        }

        return new QuizResponse(false, "Wrong answer! Please, try again.");
    }

    public void delete(final UserDetailsAdapter userDetailsAdapter, final int id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz wasn't found!"));

        if(userDetailsAdapter.getUsername().equals(quiz.getUser().getEmail())) {
            quizRepository.delete(quiz);
        } else {
            throw new DeleteQuizException("You can't delete quizzes that weren't created by you");
        }
    }

    public Quiz update(final UserDetailsAdapter userDetailsAdapter, final Quiz updateQuiz, final int id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz wasn't found!"));
        if(userDetailsAdapter.getUsername().equals(quiz.getUser().getEmail())) {

            quiz.setAnswer(updateQuiz.getAnswer());
            quiz.setOptions(updateQuiz.getOptions());
            quiz.setText(updateQuiz.getText());
            quiz.setTitle(updateQuiz.getTitle());
            return quizRepository.save(quiz);
        }
        throw new RuntimeException("You can't update quizzes that weren't created by you");
    }
}
