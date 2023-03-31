package edu.co.icesi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private UUID sessionId;

    String name;

    String harFilePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
}
