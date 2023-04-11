package edu.co.icesi.service;

import io.micronaut.http.multipart.CompletedFileUpload;


public interface BlobService {

    String upload(CompletedFileUpload file);

    byte[] download(String fileName);
}
