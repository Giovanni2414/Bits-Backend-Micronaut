package edu.co.icesi.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.multipart.CompletedFileUpload;

import edu.co.icesi.dto.BlobDTO;

import java.util.UUID;
import java.util.List;

@Client
public interface BlobAPI {

    @Post(consumes = MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    HttpResponse<UUID> upload(CompletedFileUpload file);
    // Comment added just to test the CI/CD Pipeline
    @Get("/{blobId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    HttpResponse<byte[]> download(UUID blobId);

    @Get
    List<BlobDTO> listAll();

    @Delete
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    HttpResponse<byte[]> deleteBlob(UUID blobId);
}