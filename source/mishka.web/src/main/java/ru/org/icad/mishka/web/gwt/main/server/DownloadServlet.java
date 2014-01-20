package ru.org.icad.mishka.web.gwt.main.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.apache.commons.fileupload.util.Streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DownloadServlet extends HttpServlet {

  private static final String PROPERTIES_FILE = "/WEB-INF/classes/config/uploadprogress.properties";
  private static final Logger LOGGER = LoggerFactory.getLogger(DownloadServlet.class);
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
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    downloadFile(request, response);
  }

  private void downloadFile(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    String fileName = request.getParameter("file");
    fileName = URLDecoder.decode(fileName);

    boolean invalidFileName =
            null == fileName
            || fileName.isEmpty()
            || fileName.contains("\\")
            || fileName.contains("/")
            || fileName.contains("..");

    if (invalidFileName) {
      throw new IOException(String.format("error downloading file %s", fileName));
    }

    ServletOutputStream outputStream = response.getOutputStream();
    ServletContext context = getServletConfig().getServletContext();
    String mimetype = context.getMimeType(fileName);

    File file = new File(uploadDirectory, fileName);
    response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
    response.setContentLength((int) file.length());
    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));

    Streams.copy(new FileInputStream(file), outputStream, true);

    LOGGER.info(String.format("downloaded file %s", file.getAbsolutePath()));
  }
}