package ru.org.icad.mishka.web.gwt.main.shared.upload;

import java.io.Serializable;
import java.util.Date;

public class UploadedFile implements Serializable {
    private String name;
    private Date uploadedDate;
    private ImportStatus importStatus;

    public UploadedFile() {
    }

    public UploadedFile(String name, Date uploadedDate, ImportStatus importStatus) {
        this.name = name;
        this.uploadedDate = uploadedDate;
        this.importStatus = importStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ImportStatus importStatus) {
        this.importStatus = importStatus;
    }
}
