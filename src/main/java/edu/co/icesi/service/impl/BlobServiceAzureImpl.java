package edu.co.icesi.service.impl;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.io.IOException;

@Singleton
@AllArgsConstructor
public class BlobServiceAzureImpl implements BlobService {

    @Inject
    BlobServiceClient blobServiceClient;

    @Inject
    BlobContainerClient blobContainerClient;

    public String upload(CompletedFileUpload file) throws IOException {

        // Todo UUID
        BlobClient blob = blobContainerClient
                .getBlobClient(file.getFilename());
        blob.upload(file.getInputStream(),
                file.getSize(), true);

        return file.getFilename();
    }
}
