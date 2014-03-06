package ru.org.icad.mishka.web.gwt.main.client.menu;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MenuCell extends AbstractCell<MenuItem>{
    public interface MenuTemplate extends SafeHtmlTemplates{
        @Template("<table style='margin-left: 30px'><tr><td><div style='width: {0}; height:{1}; background-reapeat: norepeat; background-image: url({2})' /></td><td>{3}</td></tr></table>")
        SafeHtml renderMenuItem(String width, String height, String image, String caption);
    }

    private static final MenuTemplate TEMPLATE = GWT.create(MenuTemplate.class);

    @Override
    public void render(Context context, MenuItem menuItem, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(TEMPLATE.renderMenuItem(
                "30px",
                "30px",
                menuItem.getImage().getSafeUri().asString(),
                menuItem.getCaption())
        );
    }
}
