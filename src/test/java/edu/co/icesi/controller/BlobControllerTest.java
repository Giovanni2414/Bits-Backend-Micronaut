package edu.co.icesi.controller;


import edu.co.icesi.service.BlobService;
import edu.co.icesi.service.impl.BlobServiceAzureImpl;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
//@Requires(property = "mockito.test.enabled", defaultValue = StringUtils.FALSE, value = StringUtils.TRUE)
public class BlobControllerTest {

//    @Inject
//    private BlobService blobService;
//
//    @Inject
//    private BlobController controller;
//
//    public BlobControllerTest() {
//        MockitoAnnotations.openMocks(this);
//        this.controller = new BlobController(blobService);
//    }
//
//    @Test
//    public void testUpload() {
//        // Mock BlobService upload
//        String fileName = "example.txt";
//        when(blobService.upload(any(CompletedFileUpload.class))).thenReturn(fileName);
//
//        // Call controller
//        CompletedFileUpload file = mock(CompletedFileUpload.class);
//        HttpResponse<String> response = controller.upload(file);
//
//        // Verify response
//        assertEquals(HttpStatus.OK, response.getStatus());
////        assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getContentType().get());
//        assertEquals(fileName, response.getBody().get());
//    }
//    @Test
//    public void testDownload() {
//        // Mock BlobService download
//        byte[] data = "Hello, world!".getBytes();
//        String fileName = "example.txt";
//        when(blobService.download(fileName)).thenReturn(data);
//
//        // Call controller
//        HttpResponse<byte[]> response = controller.download(fileName);
//
//        // Verify response
//        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, response.getContentType().get());
//        assertEquals(data, response.getBody().get());
//    }
//    @MockBean(BlobServiceAzureImpl.class)
//    BlobService blobService() {
//        return mock(BlobService.class);
//    }
}
