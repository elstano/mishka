package ru.org.icad.mishka.web.gwt.main.client.upload.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by Boss on 12/14/13.
 */
public interface UploadBundle extends ClientBundle {
    public UploadBundle INSTANCE = GWT.create(UploadBundle.class);

    ImageResource excel();

    ImageResource uploaded();

    ImageResource loading();

    ImageResource pending();

    @Source("upload.css")
    UploadCss css();
}
