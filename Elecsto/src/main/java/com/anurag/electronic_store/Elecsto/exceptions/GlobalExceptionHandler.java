package com.anurag.electronic_store.Elecsto.exceptions;//package com.anurag.electronic_store.Elecsto.exceptions;


import com.anurag.electronic_store.Elecsto.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Resource not found - handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        logger.info("Resource not found exception handler invoked!");
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.NOT_FOUND);
    }

    //MethodArgumentNotValidException - handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        logger.info("Resource not found exception handler invoked!");
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.BAD_REQUEST);

    }

    //MethodArgumentNotValidException - handler
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> badApiRequestExceptionHandler(BadApiRequestException e){
        logger.info("BadApiRequestException handler invoked!");
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.BAD_REQUEST);

    }

}
