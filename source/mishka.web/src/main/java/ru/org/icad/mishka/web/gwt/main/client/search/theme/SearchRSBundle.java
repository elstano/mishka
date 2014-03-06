package ru.org.icad.mishka.web.gwt.main.client.search.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface SearchRSBundle extends ClientBundle{
    public static final SearchRSBundle INSTANCE =GWT.create(SearchRSBundle.class);

    @Source("logo.png")
    ImageResource logo();

    @Source("search.css")
    SearchCss css();
}
