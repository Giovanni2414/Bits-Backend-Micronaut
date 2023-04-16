package edu.co.icesi.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.multipart.CompletedFileUpload;


@Client
public interface BlobAPI {

    @Post(consumes = MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    HttpResponse<String> upload(CompletedFileUpload file);

    @Get("/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    HttpResponse<byte[]> download(String fileName);

    @Get()
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<byte[]> listAll(String fileName);
}