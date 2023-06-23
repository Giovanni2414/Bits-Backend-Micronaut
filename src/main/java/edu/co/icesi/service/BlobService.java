package edu.co.icesi.service;

import io.micronaut.http.multipart.CompletedFileUpload;

import java.util.UUID;

import edu.co.icesi.model.Blob;


public interface BlobService {

    UUID upload(CompletedFileUpload file);

    byte[] download(UUID fileName);

    java.util.List<Blob> listBlobs();

    String deleteBlob(UUID blobId);
}
