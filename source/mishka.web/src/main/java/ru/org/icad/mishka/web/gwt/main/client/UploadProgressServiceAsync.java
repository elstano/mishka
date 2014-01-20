package ru.org.icad.mishka.web.gwt.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.org.icad.mishka.web.gwt.main.common.dto.FileDto;
import ru.org.icad.mishka.web.gwt.main.common.event.Event;

public interface UploadProgressServiceAsync {

  void initialise(AsyncCallback<Void> asyncCallback);

  void countFiles(AsyncCallback<Integer> asyncCallback);

  void readFiles(int page, int pageSize, AsyncCallback<List<FileDto>> asyncCallback);

  void getEvents(AsyncCallback<List<Event>> asyncCallback);
}
