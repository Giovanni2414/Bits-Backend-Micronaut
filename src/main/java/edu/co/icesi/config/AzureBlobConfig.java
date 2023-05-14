package edu.co.icesi.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;


@Factory
public class AzureBlobConfig {
    @Value("${azure.storage.connection.string}")
    private String connectionString;

    @Value("${azure.storage.container.name}")
    private String containerName;

    @Singleton
    public BlobServiceClient clobServiceClient() {

        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    @Singleton
    public BlobContainerClient blobContainerClient() {

        return clobServiceClient()
                .getBlobContainerClient(containerName);
    }
}
