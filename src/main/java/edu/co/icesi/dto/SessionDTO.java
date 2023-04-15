package edu.co.icesi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.co.icesi.constants.ErrorConstants;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class SessionDTO {

    private UUID sessionId;

    @NotNull(message = ErrorConstants.SESSION_NAME_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = ErrorConstants.SESSION_NAME_INVALID)
    String name;

    @NotNull(message = ErrorConstants.HAR_FILE_PATH_REQUIRED)
    String harFilePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
}
