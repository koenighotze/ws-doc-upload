package org.koenighotze.docuploadws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Sample repo for storing and retrieving docs from in memory hashmap.
 * 
 * @author dschmitz
 * 
 */
@Component
public class DocumentRespository {
	private static final Logger LOGGER = Logger.getLogger(DocumentRespository.class); 
	private static final Map<String, DocumentElem> STORE = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() throws URISyntaxException, IOException {
		for (String name : Arrays.asList("CopyofHECTORGRAEME.pdf",
				"HECTORGRAEME.pdf")) {
			LOGGER.info("Storing document " + name);
			URI uri = DocumentRespository.class.getResource(name).toURI();
			byte[] bytes = Files.readAllBytes(Paths.get(uri));
			
			DocumentElem elem = newDocumentElem(name, bytes,	"text/pdf", "sample doc");
			elem.setId(name);
			LOGGER.info("Stored under id " + storeDocument(elem));
		}
	}

	public void clear() {
		DocumentRespository.STORE.clear();
	}
	
	public int getNumberOfDocuments() {
		return DocumentRespository.STORE.size();
	}

	public DocumentElem getDocument(String id) {
		return DocumentRespository.STORE.get(id);
	}

	public String storeDocument(DocumentElem elem) {
		if (null == elem.getId()) {
			elem.setId(UUID.randomUUID().toString());
		}
		DocumentRespository.STORE.put(elem.getId(), elem);
		return elem.getId();
	}

	public DocumentElem newDocumentElem(String name, byte[] bytes, String mimeType,
			String description) {
		DocumentElem elem = new DocumentElem(name,  bytes, mimeType);
		elem.setDescription(description);
		return elem;
	}

}
