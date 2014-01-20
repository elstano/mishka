package ru.org.icad.mishka.web.gwt.main.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import ru.org.icad.mishka.web.gwt.main.client.view.UploadProgressView;

public final class UploadProgress implements EntryPoint {

  private static final UploadProgressView UPLOAD_PROGRESS_VIEW = new UploadProgressView();

  @Override
  public void onModuleLoad() {

    RootPanel rootPanel = RootPanel.get();

    rootPanel.add(UPLOAD_PROGRESS_VIEW);
  }
}
