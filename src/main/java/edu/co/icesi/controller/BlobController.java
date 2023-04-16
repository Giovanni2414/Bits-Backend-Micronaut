package edu.co.icesi.controller;

import com.sun.istack.Nullable;
import edu.co.icesi.api.BlobAPI;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.multipart.CompletedFileUpload;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Controller("/blobs")
public class BlobController implements BlobAPI {

    private BlobService blobService;

    @Override
    public HttpResponse<String> upload(@Nullable CompletedFileUpload file) {

        String filename = blobService.upload(file);

        return HttpResponse.ok(filename);
    }

    @Override
    public HttpResponse<byte[]> download(@PathVariable @NotBlank String fileName) {
        byte[] body = blobService.download(fileName);
        return HttpResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
    }

    @Override
    public HttpResponse<byte[]> listAll(String fileName) {
        return null;
    }


}
