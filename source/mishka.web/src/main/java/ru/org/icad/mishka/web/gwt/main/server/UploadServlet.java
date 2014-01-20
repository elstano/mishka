package ru.org.icad.mishka.web.gwt.main.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.servlet.http.HttpServlet;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UploadServlet extends HttpServlet {

  private static final String PROPERTIES_FILE = "/WEB-INF/classes/config/uploadprogress.properties";
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadServlet.class);
  private static final String FILE_SEPERATOR = System.getProperty("file.separator");
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
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    try {
      uploadFile(request);
    } catch (FileUploadException fue) {
      throw new ServletException(fue);
    }
  }

  private void uploadFile(final HttpServletRequest request) throws FileUploadException, IOException {

    if (!ServletFileUpload.isMultipartContent(request)) {
      throw new FileUploadException("error multipart request not found");
    }

    FileItemFactory fileItemFactory = new DiskFileItemFactory();
    ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
      servletFileUpload.setHeaderEncoding("UTF-8");

    FileItemIterator fileItemIterator = servletFileUpload.getItemIterator(request);

    HttpSession session = request.getSession();
    UploadProgress uploadProgress = UploadProgress.getUploadProgress(session);

    while (fileItemIterator.hasNext()) {
      FileItemStream fileItemStream = fileItemIterator.next();

      String filePath = fileItemStream.getName();
      String fileName = filePath.substring(filePath.lastIndexOf(FILE_SEPERATOR) + 1);

      UploadProgressListener uploadProgressListener = new UploadProgressListener(fileName, uploadProgress);

      UploadProgressInputStream inputStream = new UploadProgressInputStream(fileItemStream.openStream(), request.getContentLength());
      inputStream.addListener(uploadProgressListener);

        File dir = new File(uploadDirectory);
        if(!dir.exists()) {
            dir.mkdir();
        }

      File file = new File(uploadDirectory, fileName);

      Streams.copy(inputStream, new FileOutputStream(file), true);

      LOGGER.info(String.format("uploaded file %s", file.getAbsolutePath()));
    }
  }
}
