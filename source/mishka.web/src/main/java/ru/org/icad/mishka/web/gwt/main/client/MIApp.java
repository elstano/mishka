package ru.org.icad.mishka.web.gwt.main.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * User: Boss
 * Date: 10/6/13
 * Time: 10:41 PM
 */
public class MIApp {
    public static final EventBus BUS = GWT.create(SimpleEventBus.class);
}
