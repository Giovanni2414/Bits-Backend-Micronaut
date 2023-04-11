package edu.co.icesi.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.multipart.CompletedFileUpload;


@Client
public interface BlobAPI {
    HttpResponse<String> upload(CompletedFileUpload file);

    HttpResponse<byte[]> download(String fileName);
}