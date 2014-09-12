package org.koenighotze.docuploadws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * Sample repo for storing and retrieving docs from in memory hashmap.
 * 
 * @author dschmitz
 * 
 */
@Component
public class DocumentRespository {

	private static final Map<String, DocumentElem> STORE = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() throws URISyntaxException, IOException {
		for (String name : Arrays.asList("CopyofHECTORGRAEME.pdf",
				"HECTORGRAEME.pdf")) {
			URI uri = DocumentRespository.class.getResource(name).toURI();
			byte[] bytes = Files.readAllBytes(Paths.get(uri));

			DocumentRespository.STORE.put(name, new DocumentElem(name, bytes,
					"text/pdf"));
		}
	}

	public void clear() {
		DocumentRespository.STORE.clear();
	}
	
	public int getNumberOfDocuments() {
		return DocumentRespository.STORE.size();
	}

	public DocumentElem getDocument(String name) {
		return DocumentRespository.STORE.get(name);
	}

	public void storeDocument(DocumentElem elem) {
		DocumentRespository.STORE.put(elem.getName(), elem);
	}

}
