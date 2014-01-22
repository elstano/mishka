package ru.org.icad.mishka.web.gwt.main.client.menu.dev;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.menu.OptionPresenter;

import java.util.List;

public class DevPresenter implements OptionPresenter {
    private DevServiceAsync devServiceAsync = GWT.create(DevService.class);
    private FlexTable layout;
    CellList<String> cellList;

    @Override
    public void presentOption() {
        layout = new FlexTable();
        devServiceAsync.getDbTableNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throw new RuntimeException(throwable);
            }

            @Override
            public void onSuccess(List<String> dbTableNames) {
                initAsync(dbTableNames);
            }
        });
    }

    private void initAsync(List<String> dbTableNames) {
        layout = new FlexTable();

        TextCell textCell = new TextCell();

        cellList = new CellList<String>(textCell);
        cellList.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
        cellList.setSelectionModel(selectionModel);

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    devServiceAsync.getTableContent(selected, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            throw new RuntimeException(throwable);
                        }

                        @Override
                        public void onSuccess(String strings) {
                            final PopupPanel popup = new PopupPanel(true);

                            VerticalPanel verticalPanel = new VerticalPanel();
                            HTML html = new HTML("<p>"+strings+"</p>");
                            verticalPanel.add(html);

                            popup.add(verticalPanel);
                            popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                                @Override
                                public void setPosition(int offsetWidth, int offsetHeight) {
                                    int left = (Window.getClientWidth() - offsetWidth) / 3;
                                    int top = (Window.getClientHeight() - offsetHeight) / 3;
                                    popup.setPopupPosition(left, top);
                                }
                            });

                                                    }
                    });

//                    Window.alert("You selected: " + selected);
                }
            }
        });

        cellList.setRowCount(dbTableNames.size(), true);

        // Push the data into the widget.
        cellList.setRowData(0, dbTableNames);
        MainView.instance.getMainLayout().add(cellList);
    }

    @Override
    public void destroy() {
        cellList.removeFromParent();
    }
}

