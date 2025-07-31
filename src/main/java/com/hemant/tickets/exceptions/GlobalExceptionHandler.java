package com.hemant.tickets.exceptions;


import com.hemant.tickets.dto.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException ex){
        log.info("Caught QrCodeGenerationException" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to generate QrCode");
        return new ResponseEntity<>(errorDto , HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException ex){
        log.info("Caught EventUpdateException" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to update Event");
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFoundException(TicketTypeNotFoundException ex){
        log.info("Caught TicketTypeNotFoundException" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Ticket type not found ");
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex){
        log.info("Caught EventNotFoundException" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Event not found ");
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex){
        log.info("Caught UserNotFoundException" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("User not found ");
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.info("Caught MethodArgumentNotValidException" , ex);
        ErrorDto errorDto = new ErrorDto();

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldError = bindingResult.getFieldErrors();
        String validationErrorOccurred = fieldError.stream().findFirst().map(fieldError1 -> fieldError1.getField() + " : " + fieldError1.getDefaultMessage()).orElse("Validation error occurred");
        errorDto.setError(validationErrorOccurred);
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex){
        log.info("Caught ConstraintViolationException" , ex);
        ErrorDto errorDto = new ErrorDto();

        String errorMessage = ex.getConstraintViolations().stream().findFirst()
                .map(voilation -> voilation.getPropertyPath() + " : " + voilation.getMessage())
                        .orElse("Constraint violation occurred");

        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto , HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        log.info("Caught Exception" , ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unknown error occurred");
        return new ResponseEntity<>(errorDto , HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
