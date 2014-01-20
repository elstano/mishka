package ru.org.icad.mishka.web.gwt.main.server;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import ru.org.icad.mishka.web.gwt.main.common.event.Event;

public final class UploadProgress {

  private static final String SESSION_KEY = "uploadProgress";
  private List<Event> events = new ArrayList<Event>();

  private UploadProgress() {
  }

  public List<Event> getEvents() {

    return events;
  }

  public void add(final Event event) {
    events.add(event);
  }

  public void clear() {
    events = new ArrayList<Event>();
  }

  public boolean isEmpty() {
    return events.isEmpty();
  }

  public static UploadProgress getUploadProgress(final HttpSession session) {
    Object attribute = session.getAttribute(SESSION_KEY);
    if (null == attribute) {
      attribute = new UploadProgress();
      session.setAttribute(SESSION_KEY, attribute);
    }

    return null == attribute ? null : (UploadProgress) attribute;
  }
}
