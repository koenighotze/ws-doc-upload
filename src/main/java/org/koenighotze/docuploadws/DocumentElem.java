package org.koenighotze.docuploadws;

public class DocumentElem {
    private String mimeType;
    private String name;
    private byte[] bytes;
    private String description = "";
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return String.format("Id: %s, Name: %s, MimeType: %s, Size: %d", this.id, this.name, this.mimeType, this.bytes.length);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public byte[] getDocumentAsBytes() {
        return this.bytes.clone();
    }

    public void setDescription(String description) {
        this.description = description;

    }
}
