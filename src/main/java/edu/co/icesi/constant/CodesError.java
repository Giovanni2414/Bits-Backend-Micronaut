package edu.co.icesi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodesError {

    CODES_01("CODE_01","Unahuthorized: error debe estar autenticado para realizar el request");

    private String code;
    private String message;

}
