package edu.co.icesi.service.impl;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import edu.co.icesi.constants.ErrorConstants;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Singleton
@AllArgsConstructor
public class BlobServiceAzureImpl implements BlobService {

    @Inject
    BlobServiceClient blobServiceClient;

    @Inject
    BlobContainerClient blobContainerClient;

    public String upload(CompletedFileUpload file) throws IOException {

        String fileId = UUID.randomUUID().toString();

        BlobClient blob = blobContainerClient
                .getBlobClient(fileId);

        try {
            blob.upload(file.getInputStream(), file.getSize(), false);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.INTERNAL_SERVER_ERROR,
                    new VarxenPerformanceError(ErrorConstants.FILE_ALREADY_EXISTS, ErrorConstants.FILE_ALREADY_EXISTS));
        }

        return fileId;
    }

    @Override
    public byte[] download(String fileName) throws IOException {

        BlobClient blob = blobContainerClient.getBlobClient(fileName);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            blob.downloadStream(outputStream);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.INTERNAL_SERVER_ERROR,
                    new VarxenPerformanceError(ErrorConstants.FILE_NOT_FOUND, ErrorConstants.FILE_NOT_FOUND));
        }

        return outputStream.toByteArray();
    }
}
