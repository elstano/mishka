package ru.org.icad.mishka.web.gwt.main.client.view;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import ru.org.icad.mishka.web.gwt.main.client.controller.ProgressController;
import ru.org.icad.mishka.web.gwt.main.client.state.UploadProgressState;
import ru.org.icad.mishka.web.gwt.main.common.dto.FileDto;

public final class FileList extends Composite {

  private FlexTable filesTable;

  public FileList() {

    filesTable = new FlexTable();
    filesTable.getRowFormatter().addStyleName(0, "FileListHead");

    Panel filesPanel = new VerticalPanel();
    filesPanel.setStyleName("FileList");
    filesPanel.add(filesTable);

    this.initWidget(filesPanel);

    UploadProgressState.INSTANCE.addPropertyChangeListener("page", new PageListener());
    UploadProgressState.INSTANCE.addPropertyChangeListener("files", new FilesListener());
  }

  @Override
  protected void onLoad() {
    ProgressController.INSTANCE.countFiles();
    ProgressController.INSTANCE.findFiles(UploadProgressState.INSTANCE.getPage(), UploadProgressState.INSTANCE.getPageSize());
  }

  private final class FilesListener implements PropertyChangeListener {

    @Override
    public void propertyChange(final PropertyChangeEvent event) {

      List<FileDto> files = (List<FileDto>) event.getNewValue();

      filesTable.clear(true);

      filesTable.setText(0, 0, "Upload File");
      filesTable.setText(0, 1, "Upload Date");

      for (int i = 0; i < files.size(); i++) {
        FileDto file = files.get(i);
        String fileName = file.getFilename();

        Anchor anchor = new Anchor(fileName, "mishka_gwt/uploadprogress/download?file=" + fileName);

        int row = i + 1;
        filesTable.setWidget(row, 0, anchor);
        filesTable.setText(row, 1, DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT).format(file.getDateUploaded()));
      }
    }
  }

  private static final class PageListener implements PropertyChangeListener {

    @Override
    public void propertyChange(final PropertyChangeEvent event) {
      ProgressController.INSTANCE.findFiles(UploadProgressState.INSTANCE.getPage(), UploadProgressState.INSTANCE.getPageSize());
    }
  }
}
