package edu.co.icesi.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VarxenPerformanceError {
        private String code;
        private String message;
}
