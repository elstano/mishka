package ru.org.icad.mishka.web.gwt.main.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class MIApp {
    public static final EventBus BUS = GWT.create(SimpleEventBus.class);
}
