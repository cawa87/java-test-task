package ru.smarty.accountapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends IllegalArgumentException {
    public BadRequest(String s) {
        super(s);
    }
}
