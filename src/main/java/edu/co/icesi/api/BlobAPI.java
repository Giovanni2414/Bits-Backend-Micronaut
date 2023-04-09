package edu.co.icesi.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.multipart.CompletedFileUpload;

import java.io.IOException;

public interface BlobAPI {
    HttpResponse<String> upload(CompletedFileUpload file) throws IOException;

    HttpResponse<byte[]> download(String fileName) throws IOException;
}