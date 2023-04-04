package edu.co.icesi.service;

import io.micronaut.http.multipart.CompletedFileUpload;

import java.io.IOException;

public interface BlobService {

    String upload(CompletedFileUpload file) throws IOException;
}
