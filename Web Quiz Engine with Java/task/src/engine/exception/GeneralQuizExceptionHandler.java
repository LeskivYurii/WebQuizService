package engine.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Objects;

@ControllerAdvice
public class GeneralQuizExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) throws URISyntaxException {
        return ResponseEntity
                .status(400)
                .body(new ErrorMessage(LocalDate.now(), 400, "Bad Request",
                        new URI(((ServletWebRequest) webRequest).getRequest().getRequestURI()).getPath(),
                        Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()));
    }

    @ExceptionHandler({UserEmailAlreadyExist.class, RuntimeException.class})
    public ResponseEntity<ErrorMessage> handleUserEmailAlreadyExist(RuntimeException exception, WebRequest webRequest) throws URISyntaxException {
        return ResponseEntity
                .status(400)
                .body(toErrorMessage("Bad request", 400, exception, webRequest));
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleQuizNotFoundException(QuizNotFoundException exception, WebRequest webRequest) throws URISyntaxException {
        return ResponseEntity
                .status(404)
                .body(toErrorMessage("Bad request", 404, exception, webRequest));
    }

    @ExceptionHandler(DeleteQuizException.class)
    public ResponseEntity<ErrorMessage> handleDeleteQuizException(DeleteQuizException exception, WebRequest webRequest) throws URISyntaxException {
        return ResponseEntity
                .status(403)
                .body(toErrorMessage("Bad request", 403, exception, webRequest));
    }

    private ErrorMessage toErrorMessage(String error, int code, RuntimeException ex, WebRequest webRequest)
            throws URISyntaxException {
        return new ErrorMessage(LocalDate.now(), code, error
                , new URI(((ServletWebRequest) webRequest).getRequest().getRequestURI()).getPath(),
                ex.getMessage());
    }

}
