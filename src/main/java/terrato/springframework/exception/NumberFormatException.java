package terrato.springframework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by onenight on 2018-03-15.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumberFormatException extends java.lang.NumberFormatException {

    public NumberFormatException() {
        super();
    }

    public NumberFormatException(String s) {
        super(s);
    }

}
