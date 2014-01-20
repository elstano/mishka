package ru.org.icad.mishka.web.gwt.main.client.controller;

import com.google.gwt.core.client.GWT;
import ru.org.icad.mishka.web.gwt.main.client.UploadProgressService;
import ru.org.icad.mishka.web.gwt.main.client.UploadProgressServiceAsync;

public abstract class AbstractController {

  protected static final UploadProgressServiceAsync SERVICE = GWT.create(UploadProgressService.class);
}
