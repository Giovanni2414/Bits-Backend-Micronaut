package edu.co.icesi.controller;

import com.sun.istack.Nullable;
import edu.co.icesi.api.BlobAPI;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.multipart.CompletedFileUpload;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@Controller("/blobs")
public class BlobController implements BlobAPI {

    private BlobService blobService;

    @Override
    @Post(consumes = MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> upload(@Nullable CompletedFileUpload file) throws IOException {

        String filename = blobService.upload(file);

        return HttpResponse.ok(filename);
    }
}
