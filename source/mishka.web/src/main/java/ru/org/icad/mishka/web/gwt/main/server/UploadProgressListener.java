package ru.org.icad.mishka.web.gwt.main.server;

import ru.org.icad.mishka.web.gwt.main.common.event.UploadProgressChangeEvent;
import org.apache.commons.fileupload.ProgressListener;

public final class UploadProgressListener implements ProgressListener {

  private static final double COMPLETE_PERECENTAGE = 100d;
  private int percentage = -1;
  private String fileName;
  private UploadProgress uploadProgress;

  public UploadProgressListener(final String fileName, final UploadProgress uploadProgress) {
    this.fileName = fileName;
    this.uploadProgress = uploadProgress;
  }

  @Override
  public void update(final long bytesRead, final long totalBytes, final int items) {
    int percentage = (int) Math.floor(((double) bytesRead / (double) totalBytes) * COMPLETE_PERECENTAGE);

    if (this.percentage == percentage) {
      return;
    }

    this.percentage = percentage;

    UploadProgressChangeEvent event = new UploadProgressChangeEvent();
    event.setFilename(this.fileName);
    event.setPercentage(percentage);

    synchronized (this.uploadProgress) {
      this.uploadProgress.add(event);
      this.uploadProgress.notifyAll();
    }
  }
}
