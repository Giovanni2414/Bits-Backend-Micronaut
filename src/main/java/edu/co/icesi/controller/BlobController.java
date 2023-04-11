package edu.co.icesi.controller;

import com.sun.istack.Nullable;
import edu.co.icesi.api.BlobAPI;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Controller("/blobs")
public class BlobController implements BlobAPI {

    private BlobService blobService;

    @Override
    @Post(consumes = MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> upload(@Nullable CompletedFileUpload file) {

        String filename = blobService.upload(file);

        return HttpResponse.ok(filename);
    }

    @Override
    @Get("/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<byte[]> download(@PathVariable @NotBlank String fileName) {
        byte[] body = blobService.download(fileName);
        return HttpResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
    }
}
