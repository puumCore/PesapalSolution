package com.pesapal.problem3.PesapalSolution._exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = "https://github.com/puumCore">GitHub: Mandela Muriithi</a>
 * @version 1.3
 * @since 19/07/2022
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Slf4j
public class FailureException extends RuntimeException {

    public FailureException(String message) {
        super(message);
        log.error(super.getMessage());
    }

    public FailureException(String message, Object... args) {
        super(String.format(message, args));
        log.error(super.getMessage());
    }

}
