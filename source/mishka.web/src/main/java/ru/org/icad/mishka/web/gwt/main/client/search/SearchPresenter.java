package ru.org.icad.mishka.web.gwt.main.client.search;

import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.search.theme.SearchRSBundle;
import com.google.gwt.user.client.ui.*;

/**
 * User: Boss
 * Date: 10/7/13
 * Time: 1:37 AM
 */
public class SearchPresenter {
    private Grid topGrid;
    private TextBox searchInput;
    private Button searchButton;

    public void initSearchPanel(){
        topGrid = new Grid(1,4);

        SearchRSBundle.INSTANCE.css().ensureInjected();
        topGrid.addStyleName(SearchRSBundle.INSTANCE.css().searchTopTable());
        Image logo = new Image(SearchRSBundle.INSTANCE.logo());
        topGrid.setWidget(0, 0, logo);

        HTML placeholder = new HTML("<div style='width: 60px;'>&nbsp;</div>");
        topGrid.setWidget(0,1, placeholder);

        searchInput = new TextBox();
        searchInput.setWidth("700px");
        topGrid.setWidget(0,2,searchInput);

        searchButton = new Button("Search");
        topGrid.setWidget(0,3,searchButton);

        MainView.instance.getMainLayout().addNorth(topGrid, 75);
    }
}
