package com.test.common;

import com.test.common.exceptions.base.NonPersistedAccountUpdateException;
import com.test.common.exceptions.storage.StorageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleException(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<Object> handleException(StorageException ex,  WebRequest request) {
        return new ResponseEntity<Object>(
                ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NonPersistedAccountUpdateException.class)
    public ResponseEntity<Object> handleException(NonPersistedAccountUpdateException ex,  WebRequest request) {
        return new ResponseEntity<Object>(
                ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
