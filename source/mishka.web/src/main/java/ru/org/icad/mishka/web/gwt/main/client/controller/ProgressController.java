package ru.org.icad.mishka.web.gwt.main.client.controller;

import com.google.gwt.core.client.GWT;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.org.icad.mishka.web.gwt.main.client.state.UploadProgressState;
import ru.org.icad.mishka.web.gwt.main.common.dto.FileDto;
import ru.org.icad.mishka.web.gwt.main.common.event.Event;
import ru.org.icad.mishka.web.gwt.main.common.event.UploadProgressChangeEvent;

public final class ProgressController extends AbstractController {

  public static final ProgressController INSTANCE = new ProgressController();

  private ProgressController() {
  }

  public void findFiles(final int page, final int pageSize) {
    SERVICE.readFiles(page, pageSize, new AsyncCallback<List<FileDto>>() {

      @Override
      public void onFailure(final Throwable t) {
        GWT.log("error find files", t);
      }

      @Override
      public void onSuccess(final List<FileDto> files) {
        UploadProgressState.INSTANCE.setFiles(files);
      }
    });
  }

  private void getEvents() {

    SERVICE.getEvents(new AsyncCallback<List<Event>>() {

      @Override
      public void onFailure(final Throwable t) {
        GWT.log("error get events", t);
      }

      @Override
      public void onSuccess(final List<Event> events) {

        for (Event event : events) {
          handleEvent(event);
        }
        SERVICE.getEvents(this);
      }

      private void handleEvent(final Event event) {

        if (event instanceof UploadProgressChangeEvent) {
          UploadProgressChangeEvent uploadPercentChangeEvent = (UploadProgressChangeEvent) event;
          String filename = uploadPercentChangeEvent.getFilename();
          Integer percentage = uploadPercentChangeEvent.getPercentage();

          UploadProgressState.INSTANCE.setUploadProgress(filename, percentage);
        }
      }
    });
  }

  public void initialise() {
    SERVICE.initialise(new AsyncCallback<Void>() {

      @Override
      public void onFailure(final Throwable t) {
        GWT.log("error initialise", t);
      }

      @Override
      public void onSuccess(final Void result) {
        getEvents();
      }
    });
  }

  public void countFiles() {
    SERVICE.countFiles(new AsyncCallback<Integer>() {

      @Override
      public void onFailure(final Throwable t) {
        GWT.log("error count files", t);
      }

      @Override
      public void onSuccess(final Integer result) {
        int pageSize = UploadProgressState.INSTANCE.getPageSize();
        int pages = (int) Math.ceil((double) result / (double) pageSize);
        UploadProgressState.INSTANCE.setPages(pages);
      }
    });
  }
}
