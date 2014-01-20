package ru.org.icad.mishka.web.gwt.main.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import ru.org.icad.mishka.web.gwt.main.client.UploadProgressService;
import ru.org.icad.mishka.web.gwt.main.common.dto.FileDto;
import ru.org.icad.mishka.web.gwt.main.common.event.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UploadProgressServlet extends RemoteServiceServlet implements UploadProgressService {

  private static final int EVENT_WAIT = 30 * 1000;
  private static final String PROPERTIES_FILE = "/WEB-INF/classes/config/uploadprogress.properties";
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadProgressServlet.class);
  private String uploadDirectory;

  @Override
  public void init() throws ServletException {
    Properties properties = new Properties();
    try {
      properties.load(getServletContext().getResourceAsStream(PROPERTIES_FILE));
    } catch (IOException ioe) {
      throw new ServletException(ioe);
    }

    uploadDirectory = properties.getProperty("upload.directory", "target");
  }

  @Override
  public void initialise() {
    getThreadLocalRequest().getSession(true);
  }

  @Override
  public List<FileDto> readFiles(final int page, final int pageSize) {

    File[] listFiles = readFiles(this.uploadDirectory);
    sortFiles(listFiles);

    int firstFile = pageSize * (page - 1);
    int lastFile = firstFile + pageSize;

    int fileCount = listFiles.length;
    if (fileCount < lastFile) {
      lastFile = fileCount;
    }

    if (firstFile < fileCount) {
      List<FileDto> files = new ArrayList<FileDto>();

      for (int i = firstFile; i < lastFile; i++) {

        File file = listFiles[i];
        FileDto fileDto = new FileDto();
        fileDto.setFilename(file.getName());
        fileDto.setDateUploaded(new Date(file.lastModified()));
        files.add(fileDto);
      }
      return files;
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public List<Event> getEvents() {

    HttpSession session = getThreadLocalRequest().getSession();
    UploadProgress uploadProgress = UploadProgress.getUploadProgress(session);

    List<Event> events = null;
    if (null != uploadProgress) {
      if (uploadProgress.isEmpty()) {
        try {
          synchronized (uploadProgress) {
            LOGGER.debug("waiting...");
            uploadProgress.wait(EVENT_WAIT);
          }
        } catch (final InterruptedException ie) {
          LOGGER.debug("interrupted...");
        }
      }

      synchronized (uploadProgress) {
        events = uploadProgress.getEvents();
        uploadProgress.clear();
      }
    }

    return events;
  }

  @Override
  public int countFiles() {
    return readFiles(this.uploadDirectory).length;
  }
  
  private File[] readFiles(final String directory) {
    File uploadDirectory = new File(directory);
    return uploadDirectory.listFiles(new FileFilter() {

      @Override
      public boolean accept(final File file) {
        return null == file ? false : file.isFile();
      }
    });
  }

  private void sortFiles(final File[] listFiles) {
    Arrays.sort(listFiles, new Comparator<File>() {

      @Override
      public int compare(final File f1, final File f2) {
        return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
      }
    });
  }  
}
