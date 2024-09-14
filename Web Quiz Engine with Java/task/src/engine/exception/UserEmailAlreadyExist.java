package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserEmailAlreadyExist extends RuntimeException {

    public UserEmailAlreadyExist(String message) {
        super(message);
    }

}
