package edu.co.icesi.service;

import io.micronaut.http.multipart.CompletedFileUpload;

import java.util.UUID;


public interface BlobService {

    UUID upload(CompletedFileUpload file);

    byte[] download(UUID fileName);
}
