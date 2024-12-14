package todo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class TodoServiceException extends RuntimeException {
    private final String entityName;
    private final HttpStatus status;
    private final String message;
}
