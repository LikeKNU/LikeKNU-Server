package ac.knu.likeknu.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<String> businessExceptionHandler(BusinessException exception) {
        String message = exception.getMessage();
        return ResponseEntity.badRequest().body(message);
    }
}
