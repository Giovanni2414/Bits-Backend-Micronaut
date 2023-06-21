package edu.co.icesi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.co.icesi.constant.CodesError;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class SessionDTO {

    private UUID sessionId;

    @NotNull(message = CodesError.SESSION_NAME_REQUIRED_CONSTANT)
    String name;

    @NotNull(message = CodesError.HAR_FILE_PATH_REQUIRED_CONSTANT)
    UUID harFilePath;

    @NotNull(message = CodesError.USERNAME_REQUIRED_CONSTANT)
    String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
}
