package edu.co.icesi.mapper;


import edu.co.icesi.dto.SessionDTO;
import edu.co.icesi.model.Blob;
import edu.co.icesi.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "jsr330")
public interface SessionMapper {

    @Mapping(source = "harFilePath", target = "blob.blobId")
    @Mapping(source = "username", target = "user.username")
    Session toSession(SessionDTO sessionDTO);

    @Mapping(source = "blob.blobId", target = "harFilePath")
    SessionDTO toSessionDTO(Session session);

    default UUID fromBlob(Blob blob) {
        return blob.getBlobId();
    }

    default Blob toBlob(UUID blob) {
        return Blob.builder().blobId(blob).build();
    }
}
