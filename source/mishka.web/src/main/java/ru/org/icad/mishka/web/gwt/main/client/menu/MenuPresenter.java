package ru.org.icad.mishka.web.gwt.main.client.menu;

import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.menu.theme.MenuRSBundle;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class MenuPresenter {
    private CellList<MenuItem> menuView;
    private MenuDataManager dm;
    private SingleSelectionModel<MenuItem> selectionModel;
    private MenuItem currentMenuItem;

    public MenuPresenter() {
        dm = new MenuDataManager();
    }

    public void initMenu(){
        MenuCell textCell = new MenuCell();

        MenuRSBundle.INSTANCE.css().ensureInjected();

        menuView = new CellList<MenuItem>(textCell);
        menuView.setStyleName(MenuRSBundle.INSTANCE.css().menuCellList());
        selectionModel = new SingleSelectionModel<MenuItem>(new ProvidesKey<MenuItem>(){
            @Override
            public Object getKey(MenuItem item) {
                return item.getCaption();
            }
        });
        menuView.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                if(currentMenuItem != null&& currentMenuItem.getOptionPresenter() != null){
                    currentMenuItem.getOptionPresenter().destroy();
                }
                MenuItem selected = selectionModel.getSelectedObject();
                if (selected != null && selected.getOptionPresenter() != null) {
                    selected.getOptionPresenter().presentOption();
                }
                currentMenuItem = selected;
            }
        });

        menuView.setRowData(0, dm.getMenuItems());
        menuView.setRowCount(dm.getMenuItems().size(), true);
        selectionModel.setSelected(dm.getMenuItems().get(0), true);
        menuView.setVisible(true);
        MainView.instance.getMainLayout().addWest(menuView, 200);
    }
}
