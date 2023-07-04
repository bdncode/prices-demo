package com.inditex.pricesdemo.error;

import com.inditex.pricesdemo.constants.ErrorConstants;
import com.inditex.pricesdemo.error.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .status(notFound.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiErrorDto, notFound);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDto> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .status(badRequest.value())
                .message(ErrorConstants.NOT_VALID_PARAMS)
                .build();

        return new ResponseEntity<>(apiErrorDto, badRequest);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                .status(status.value())
                .message(ErrorConstants.METHOD_NOT_SUPPORTED)
                .build();

        return new ResponseEntity<>(apiErrorDto, status);
    }
}
