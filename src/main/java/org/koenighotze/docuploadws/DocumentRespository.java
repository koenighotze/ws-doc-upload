package org.koenighotze.docuploadws;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.apache.log4j.Logger.getLogger;

/**
 * Sample repo for storing and retrieving docs from in memory hashmap.
 *
 * @author dschmitz
 */
@Component
public class DocumentRespository {
    private static final Logger LOGGER = getLogger(DocumentRespository.class);
    private static final Map<String, DocumentElem> STORE = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws URISyntaxException, IOException {
        for (String name : asList("CopyofHECTORGRAEME.pdf",
                "HECTORGRAEME.pdf")) {
            LOGGER.info("Storing document " + name);
            URI uri = DocumentRespository.class.getResource(name).toURI();
            byte[] bytes = readAllBytes(get(uri));

            DocumentElem elem = newDocumentElem(name, bytes, "text/pdf", "sample doc");
            elem.setId(name);
            LOGGER.info("Stored under id " + storeDocument(elem));
        }
    }

    public void clear() {
        STORE.clear();
    }

    public int getNumberOfDocuments() {
        return STORE.size();
    }

    public DocumentElem getDocument(String id) {
        return STORE.get(id);
    }

    public String storeDocument(DocumentElem elem) {
        if (null == elem.getId()) {
            elem.setId(randomUUID().toString());
        }
        STORE.put(elem.getId(), elem);
        return elem.getId();
    }

    public DocumentElem newDocumentElem(String name, byte[] bytes, String mimeType,
                                        String description) {
        DocumentElem elem = new DocumentElem(name, bytes, mimeType);
        elem.setDescription(description);
        return elem;
    }

}
