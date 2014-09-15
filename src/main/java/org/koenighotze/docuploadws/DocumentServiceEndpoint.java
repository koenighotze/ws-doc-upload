package org.koenighotze.docuploadws;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.koenighotze.docuservice.GetDocumentRequest;
import org.koenighotze.docuservice.GetDocumentResponse;
import org.koenighotze.docuservice.StoreDocumentRequest;
import org.koenighotze.docuservice.StoreDocumentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DocumentServiceEndpoint {
	private static final String NAMESPACE_URI = "http://koenighotze.org/docuservice";
	private static final Logger LOGGER = Logger.getLogger(DocumentServiceEndpoint.class);
	@Autowired
	private DocumentRespository documentRespository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeDocumentRequest")
	@ResponsePayload
	public StoreDocumentResponse storeDocument(
			@RequestPayload StoreDocumentRequest request) {
		LOGGER.info("Storing document " + request.getName());
		
		DocumentElem elem = this.documentRespository.newDocumentElem(request.getName(), 
				Base64.decodeBase64(request.getBase64Content()), 
				request.getMimeType(), request.getDescription());
		String id  = this.documentRespository.storeDocument(elem);
		StoreDocumentResponse response = new StoreDocumentResponse();
		response.setId(id);
		LOGGER.info("Stored under id " + id);
		return response;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDocumentRequest")
	@ResponsePayload
	public GetDocumentResponse getDocumentByName(
			@RequestPayload GetDocumentRequest request) {
		LOGGER.info("Fetching doument " + request.getId());
		DocumentElem document = this.documentRespository.getDocument(request
				.getId());
		GetDocumentResponse response = new GetDocumentResponse();
		if (null != document) {
			response.setBase64Content(Base64.encodeBase64String(document.getDocumentAsBytes()));
			response.setDescription(document.getDescription());
			response.setId(document.getId());
			response.setName(document.getName());
			response.setMimeType(document.getMimeType());
		}
		else {
			LOGGER.warn("Document with id " + request.getId() + " not found!");
		}
		
		
		return response;
	}
}
