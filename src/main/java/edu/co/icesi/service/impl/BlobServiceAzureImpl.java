package edu.co.icesi.service.impl;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import edu.co.icesi.constants.ErrorConstants;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.Blob;
import edu.co.icesi.repository.BlobRepository;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Singleton
@AllArgsConstructor
public class BlobServiceAzureImpl implements BlobService {

    @Inject
    BlobServiceClient blobServiceClient;

    @Inject
    BlobContainerClient blobContainerClient;

    @Inject
    BlobRepository blobRepository;

    public UUID upload(CompletedFileUpload file) {

        UUID blobId = UUID.randomUUID();

        String fileId = blobId + "." + file.getContentType().get().getExtension();

        BlobClient blob = blobContainerClient.getBlobClient(fileId);

        try {
            blob.upload(file.getInputStream(), file.getSize(), false);
            Blob saved = Blob.builder().blobId(blobId).relativePath(fileId).build();
            blobRepository.save(saved);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(ErrorConstants.BLOB_NOT_CREATED, ErrorConstants.BLOB_NOT_CREATED));
        }

        return blobId;
    }

    @Override
    public byte[] download(UUID fileName) {

        Blob blob = blobRepository.findById(fileName).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND,
                new VarxenPerformanceError(ErrorConstants.FILE_NOT_FOUND, ErrorConstants.FILE_NOT_FOUND)));

        BlobClient blobClient = blobContainerClient.getBlobClient(blob.getRelativePath());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            blobClient.downloadStream(outputStream);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.INTERNAL_SERVER_ERROR,
                    new VarxenPerformanceError(ErrorConstants.FILE_NOT_FOUND, ErrorConstants.FILE_NOT_FOUND));
        }

        return outputStream.toByteArray();
    }
}
