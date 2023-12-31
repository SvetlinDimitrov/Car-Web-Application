package com.example.mobilele.web;

import com.example.mobilele.domain.dtos.exception.CustomExceptionResponse;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Model not found by id or name parameter",
            content = @Content(
                    schema = @Schema( implementation = CustomExceptionResponse.class),
                    mediaType = "application/json")
    )
    public ResponseEntity<CustomExceptionResponse> catchUserNotFoundException(NotFoundException e) {

        return new ResponseEntity<>(getErrorResponse(List.of(e.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "For invalid id format",
            content = @Content(
                    schema = @Schema( implementation = CustomExceptionResponse.class),
                    mediaType = "application/json")
    )
    public ResponseEntity<CustomExceptionResponse> catchWrongCredentialsException(WrongCredentialsException e) {

        return new ResponseEntity<>(getErrorResponse(e.getMessages()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomExceptionResponse> catchAlreadyPersistException(AlreadyExistException e) {

        return new ResponseEntity<>(getErrorResponse(List.of(e.getMessage())), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomExceptionResponse> handleMissingIdParameter(MissingServletRequestParameterException ex) {
        String message = "Bad Request :"+ ex.getMessage();
        return new ResponseEntity<>(getErrorResponse(List.of(message)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid request body. Please check your request data.";
        return new ResponseEntity<>(getErrorResponse(List.of(errorMessage , ex.getMessage().split(":")[0]))
                , HttpStatus.BAD_REQUEST);
    }

    private CustomExceptionResponse getErrorResponse(List<String> messages){
        return new CustomExceptionResponse(messages);
    }

}
