package edu.co.icesi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.co.icesi.constants.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private UUID sessionId;

    @NotNull(message = ErrorConstants.SESSION_NAME_REQUIRED)
    String name;

    @NotNull(message = ErrorConstants.HAR_FILE_PATH_REQUIRED)
    String harFilePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
}
