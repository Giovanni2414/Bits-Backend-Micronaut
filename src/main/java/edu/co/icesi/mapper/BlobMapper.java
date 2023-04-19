package edu.co.icesi.mapper;

import edu.co.icesi.dto.BlobDTO;
import edu.co.icesi.model.Blob;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface BlobMapper {

    Blob toBlob(BlobDTO blobDTO);

    BlobDTO toBlobDTO(Blob session);
}
