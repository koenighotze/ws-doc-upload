package org.koenighotze.docuploadws;

public class DocumentElem {
	private String mimeType;
	private String name;
	private byte[] bytes;
	private String description = "";

	public DocumentElem(String name, byte[] bytes, String mimeType) {
		this.name = name;
		
		this.mimeType = mimeType;
		if (null == this.mimeType) {
			this.mimeType = "unkown";
		}
		
		this.bytes = bytes.clone();
		
	}

	@Override
	public String toString() {
		return String.format("Name: %s, MimeType: %s, Size: %d", this.name, this.mimeType, this.bytes.length);
	}
	
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description ;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public byte[] getDocumentAsBytes() {
		return this.bytes.clone();
	}
}
