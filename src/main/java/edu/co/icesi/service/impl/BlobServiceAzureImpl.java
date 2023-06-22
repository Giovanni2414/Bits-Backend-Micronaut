package edu.co.icesi.service.impl;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;

import com.sun.jna.platform.win32.COM.IStream;
import edu.co.icesi.constant.CodesError;
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
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        String fileId = blobId + ".har";

        BlobClient blob = blobContainerClient.getBlobClient(fileId);

        try {
            JSONObject jsonObject = new JSONObject(new String(file.getBytes()));
            jsonObject = addId(jsonObject);
            String temp = jsonObject.toString();
            InputStream is = new ByteArrayInputStream(temp.getBytes());
            blob.upload(is, temp.getBytes().length, false);
            Blob saved = Blob.builder().blobId(blobId).relativePath(fileId).build();
            blobRepository.save(saved);
        } catch (Exception e) {
            System.out.println(e);
            throw new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.BLOB_NOT_CREATED.getCode(), CodesError.BLOB_NOT_CREATED.getMessage()));
        }

        return blobId;
    }

    private JSONObject addId(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("log").getJSONArray("entries");
        jsonObject.getJSONObject("log").remove("entries");
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArray.getJSONObject(i).put("_id", UUID.randomUUID());
            jsonArray.getJSONObject(i).remove("response");
        }
        jsonObject.getJSONObject("log").put("entries", jsonArray);

        return jsonObject;
    }

    @Override
    public byte[] download(UUID fileName) {

        Blob blob = blobRepository.findById(fileName).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND,
                new VarxenPerformanceError(CodesError.FILE_NOT_FOUND.getCode(), CodesError.FILE_NOT_FOUND.getMessage())));

        BlobClient blobClient = blobContainerClient.getBlobClient(blob.getRelativePath());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            blobClient.downloadStream(outputStream);
        } catch (Exception e) {
            throw new VarxenPerformanceException(HttpStatus.INTERNAL_SERVER_ERROR,
                    new VarxenPerformanceError(CodesError.FILE_NOT_FOUND.getCode(), CodesError.FILE_NOT_FOUND.getMessage()));
        }

        return outputStream.toByteArray();
    }

    @Override
    public List<Blob> listBlobs() {
        return new ArrayList<>(blobRepository.findAll());
    }

    @Override
    public void deleteBlob(UUID blobId) {
        Blob currentBlob = blobRepository.findById(blobId).orElseThrow(() -> new VarxenPerformanceException(HttpStatus.NOT_FOUND,
                new VarxenPerformanceError(CodesError.BLOB_NOT_DELETED.getCode(), CodesError.BLOB_NOT_DELETED.getMessage())));
        BlobClient blob = blobContainerClient.getBlobClient(currentBlob.getRelativePath());
        blob.delete();
        blobRepository.delete(currentBlob);
    }

}
