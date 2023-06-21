package edu.co.icesi.controller;

import com.sun.istack.Nullable;
import java.util.List;

import edu.co.icesi.api.BlobAPI;
import edu.co.icesi.dto.BlobDTO;
import edu.co.icesi.mapper.BlobMapper;
import edu.co.icesi.service.BlobService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller("/blobs")
@Secured(SecurityRule.IS_ANONYMOUS)
public class BlobController implements BlobAPI {

    @Inject
    private BlobService blobService;

    @Inject
    private BlobMapper blobMapper;

    @Override
    public HttpResponse<UUID> upload(@Nullable CompletedFileUpload file) {

        UUID filename = blobService.upload(file);

        return HttpResponse.ok(filename);
    }

    @Override
    public HttpResponse<byte[]> download(@PathVariable @NotBlank UUID blobId) {
        byte[] body = blobService.download(blobId);
        return HttpResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(body);
    }

    @Override
    public List<BlobDTO> listAll() {
        return blobService.listBlobs().stream().map(blobMapper::toBlobDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteBlob(UUID blobId) {
        blobService.deleteBlob(blobId);
    }


}
