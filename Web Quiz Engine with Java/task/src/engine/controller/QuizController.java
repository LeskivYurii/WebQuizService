package engine.controller;

import engine.domain.QuizCompletedInfo;
import engine.domain.Quiz;
import engine.domain.QuizResponse;
import engine.domain.UserDetailsAdapter;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable final int id) {
        return quizService.findById(id);
    }

    @GetMapping
    public Page<Quiz> getQuizzes(@RequestParam int page) {
        return quizService.findAll(page);
    }

    @GetMapping("/completed")
    public Page<QuizCompletedInfo> getCompletedQuizzes(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @RequestParam int page) {
        return quizService.findCompletedAll(userDetailsAdapter, page);
    }

    @PostMapping
    public Quiz create(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @RequestBody @Valid final Quiz newQuiz) {
        return quizService.create(userDetailsAdapter, newQuiz);
    }

    @PostMapping("/{id}/solve")
    public QuizResponse respondToQuiz(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @PathVariable final int id, @RequestBody final Map<String, List<Integer>> answer) {
        return quizService.solveQuiz(userDetailsAdapter, id, answer.get("answer"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @PathVariable int id) {
        quizService.delete(userDetailsAdapter, id);
    }

    @PutMapping("/{id}")
    public Quiz update(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter,
                          @RequestBody @Valid Quiz quiz, @PathVariable int id) {
        return quizService.update(userDetailsAdapter, quiz, id);
    }

}
