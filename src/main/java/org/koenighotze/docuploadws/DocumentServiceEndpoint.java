package org.koenighotze.docuploadws;

import org.apache.commons.codec.binary.Base64;
import org.koenighotze.docuservice.GetDocumentRequest;
import org.koenighotze.docuservice.GetDocumentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DocumentServiceEndpoint {
	private static final String NAMESPACE_URI = "http://koenighotze.org/docuservice";

	@Autowired
	private DocumentRespository documentRespository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDocumentRequest")
	@ResponsePayload
	public GetDocumentResponse getDocumentByName(
			@RequestPayload GetDocumentRequest request) {
		DocumentElem document = this.documentRespository.getDocument(request
				.getName());
		GetDocumentResponse response = new GetDocumentResponse();
		if (null != document) {
			response.setBase64Content(Base64.encodeBase64String(document.getDocumentAsBytes()));
			response.setDescription(document.getDescription());
			response.setId(document.getName());
			response.setMimeType(document.getMimeType());
		}
		return response;
	}
}
