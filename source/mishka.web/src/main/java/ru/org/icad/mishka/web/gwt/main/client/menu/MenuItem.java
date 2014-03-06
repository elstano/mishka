package ru.org.icad.mishka.web.gwt.main.client.menu;

import com.google.gwt.resources.client.ImageResource;

public class MenuItem {
    private ImageResource image;
    private String caption;
    private OptionPresenter optionPresenter;

    public MenuItem(ImageResource image, String caption, OptionPresenter optionPresenter) {
        this.image = image;
        this.caption = caption;
        this.optionPresenter = optionPresenter;
    }

    public ImageResource getImage() {
        return image;
    }

    public void setImage(ImageResource image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public OptionPresenter getOptionPresenter() {
        return optionPresenter;
    }

    public void setOptionPresenter(OptionPresenter optionPresenter) {
        this.optionPresenter = optionPresenter;
    }
}
