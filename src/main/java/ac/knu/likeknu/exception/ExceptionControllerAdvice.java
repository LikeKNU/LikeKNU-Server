package ac.knu.likeknu.exception;

import ac.knu.likeknu.service.SlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final SlackService slackService;

    public ExceptionControllerAdvice(SlackService slackService) {
        this.slackService = slackService;
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<String> businessExceptionHandler(BusinessException exception) {
        String message = exception.getMessage();
        log.info("BusinessException: ", exception);
        slackService.sendMessage(message);
        return ResponseEntity.badRequest()
                .body(message);
    }

    /*@ExceptionHandler(value = Exception.class)
    protected ResponseEntity<String> exceptionHandler(Exception exception) {
        String message = exception.getMessage();
        String stackTrace = Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining());
        log.error("Exception: ", exception);
        slackService.sendMessage(String.join("\n", message, stackTrace));
        return ResponseEntity.internalServerError()
                .body(message);
    }*/
}
