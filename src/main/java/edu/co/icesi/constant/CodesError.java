package edu.co.icesi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodesError {

    CODES_01("CODE_01","Unauthorized: Error must be authenticated to make request"),
    SESSION_NOT_FOUND("CODE_02","Session not found: The requested session does not exist"),
    SESSION_ALREADY_EXIST("CODE_03","Session already exists: The session entered already exists"),
    FILE_NOT_FOUND("CODE_04","File not found: The searched file does not exist"),
    FILE_ALREADY_EXISTS("CODE_05","File already exist: The file I am trying to enter already exists"),
    FILE_NOT_DELETED("CODE_06","File not deleted: File could not be deleted"),

    HAR_FILE_PATH_REQUIRED("CODE_07","Har file path is required: The path to the har file is required"),

    SESSION_NAME_REQUIRED("CODE_08","Session name is required: The session name is required"),
    SESSION_NAME_INVALID("CODE_09","Session name is invalid: The session name is invalid"),
    SESSION_NOT_CREATED("CODE_10","Session not created: The session could not be created correctly"),
    BLOB_NOT_CREATED("CODE_11","Blob not created: The blob could not be created"),
    CONSTRAINT_VIOLATION("CODE_12", "Constraint violation: Restrictions have been violated"),
    USER_NOT_FOUND("CODE_13","User not found: The user you are looking for was not found"),

    USER_NOT_REGISTER("CODE_14", "Invalid login:Incorrect username or password"),
    USERNAME_REQUIRED("CODE_15", "Username is required: The username is required"),
    BLOB_NOT_DELETED("CODE_16", "Blob not deleted: The blob can't be deleted");

    final public  static String USERNAME_REQUIRED_CONSTANT = "Username is required: The username is required";
    final public  static String SESSION_NAME_REQUIRED_CONSTANT = "Session name is required: The session name is required";
    final public  static String HAR_FILE_PATH_REQUIRED_CONSTANT = "Har file path is required: The path to the har file is required";
    private String code;
    private String message;

}
