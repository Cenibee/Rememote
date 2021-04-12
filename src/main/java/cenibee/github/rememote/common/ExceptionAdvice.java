package cenibee.github.rememote.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound(Throwable t) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(t));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> exists(Throwable t) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDto(t));
    }

    @Getter
    @NoArgsConstructor
    private class ExceptionDto {

        private String errorType;
        private String message;

        private ExceptionDto(Throwable t) {
            this.errorType = t.getClass().getSimpleName();
            this.message = t.getMessage();
        }
    }
}
