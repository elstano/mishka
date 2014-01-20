package ru.org.icad.mishka.web.gwt.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.org.icad.mishka.web.gwt.main.common.dto.FileDto;
import ru.org.icad.mishka.web.gwt.main.common.event.Event;

@RemoteServiceRelativePath("uploadprogress")
public interface UploadProgressService extends RemoteService {

  void initialise();

  int countFiles();

  List<FileDto> readFiles(int page, int pageSize);

  List<Event> getEvents();
}
