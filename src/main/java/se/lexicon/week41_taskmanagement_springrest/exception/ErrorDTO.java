package se.lexicon.week41_taskmanagement_springrest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private HttpStatus status;
    private String statusText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    public ErrorDTO(HttpStatus status, String statusText) {
        this.status = status;
        this.statusText = statusText;
        this.timestamp = LocalDateTime.now();
    }
}
