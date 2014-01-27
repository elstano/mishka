package ru.org.icad.mishka.web.gwt.main.client.menu.dev;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.menu.OptionPresenter;

import java.util.List;

public class DevPresenter implements OptionPresenter {
    CellList<String> cellList;
    private DevServiceAsync devServiceAsync = GWT.create(DevService.class);
    private FlexTable initializedPanel;

    @Override
    public void presentOption() {
        initializedPanel = new FlexTable();

        Button showTablesButton = new Button("Проверка загрузки данных в БД");
        showTablesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
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
        });

        Button restrictionByRawButton = new Button("Ограничение по сырцу");
        restrictionByRawButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                devServiceAsync.getTableContent(TableName.CAST_ELECTROLIZER, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }

                    @Override
                    public void onSuccess(final String tableContent) {
                        final PopupPanel popupPanel = new PopupPanel(true);

                        VerticalPanel verticalPanel = new VerticalPanel();

                        Button startCalcRestriction = new Button("Начать расчет");
                        startCalcRestriction.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent clickEvent) {
                                devServiceAsync.restrictionProcess(new AsyncCallback<Void>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {

                                    }

                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        });

                        Button showResultRestriction = new Button("Показать результат расчета");
                        showResultRestriction.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent clickEvent) {
                                final PopupPanel popup = new PopupPanel(true);

                                VerticalPanel verticalPanel = new VerticalPanel();
                                HTML html = new HTML("<p>" + tableContent + "</p>");
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

                        verticalPanel.add(startCalcRestriction);
                        verticalPanel.add(showResultRestriction);

                        popupPanel.add(verticalPanel);
                        popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                            @Override
                            public void setPosition(int offsetWidth, int offsetHeight) {
                                int left = (Window.getClientWidth() - offsetWidth) / 3;
                                int top = (Window.getClientHeight() - offsetHeight) / 3;
                                popupPanel.setPopupPosition(left, top);
                            }
                        });
                    }
                });
            }
        });

        Button castingButton = new Button("Процесс литья");
        castingButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                devServiceAsync.getTableContent(TableName.CAST, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }

                    @Override
                    public void onSuccess(final String tableContent) {
                        final PopupPanel popupPanel = new PopupPanel(true);

                        VerticalPanel verticalPanel = new VerticalPanel();

                        Button startCalcCasting = new Button("Начать расчет");
                        Button showResultCasting = new Button("Показать результат расчета");
                        showResultCasting.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent clickEvent) {
                                final PopupPanel popup = new PopupPanel(true);

                                VerticalPanel verticalPanel = new VerticalPanel();
                                HTML html = new HTML("<p>" + tableContent + "</p>");
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

                        verticalPanel.add(startCalcCasting);
                        verticalPanel.add(showResultCasting);

                        popupPanel.add(verticalPanel);
                        popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                            @Override
                            public void setPosition(int offsetWidth, int offsetHeight) {
                                int left = (Window.getClientWidth() - offsetWidth) / 3;
                                int top = (Window.getClientHeight() - offsetHeight) / 3;
                                popupPanel.setPopupPosition(left, top);
                            }
                        });
                    }
                });
            }
        });

        Button homogenizationButton = new Button("Процесс гомогенизации");
        homogenizationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                devServiceAsync.getTableContent("TEST", new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }

                    @Override
                    public void onSuccess(final String tableContent) {
                        final PopupPanel popupPanel = new PopupPanel(true);

                        VerticalPanel verticalPanel = new VerticalPanel();

                        Button startCalcRestriction = new Button("Начать расчет");
                        Button showResultRestriction = new Button("Показать результат расчета");
                        showResultRestriction.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent clickEvent) {
                                final PopupPanel popup = new PopupPanel(true);

                                VerticalPanel verticalPanel = new VerticalPanel();
                                HTML html = new HTML("<p>" + tableContent + "</p>");
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


                        verticalPanel.add(startCalcRestriction);
                        verticalPanel.add(showResultRestriction);

                        popupPanel.add(verticalPanel);
                        popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                            @Override
                            public void setPosition(int offsetWidth, int offsetHeight) {
                                int left = (Window.getClientWidth() - offsetWidth) / 3;
                                int top = (Window.getClientHeight() - offsetHeight) / 3;
                                popupPanel.setPopupPosition(left, top);
                            }
                        });
                    }
                });
            }
        });

        initializedPanel.setWidget(0, 4, showTablesButton);
        initializedPanel.setWidget(2, 4, restrictionByRawButton);
        initializedPanel.setWidget(4, 4, castingButton);
        initializedPanel.setWidget(6, 4, homogenizationButton);

        MainView.instance.getMainLayout().add(initializedPanel);

    }

    private void initAsync(List<String> dbTableNames) {
        final PopupPanel popupPanel = new PopupPanel(true);

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
                            HTML html = new HTML("<p>" + strings + "</p>");
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

//        cellList.setRowCount(dbTableNames.size(), false);
//
//        // Push the data into the widget.
//        cellList.setRowData(0, dbTableNames);
//        CellList<String> cellList = new CellList<String>(new TextCell());

        // Add a cellList to a data provider.
        ListDataProvider<String> dataProvider = new ListDataProvider<String>();
        List<String> data = dataProvider.getList();
        for (String dbTableName : dbTableNames) {
            data.add(dbTableName);
        }
        dataProvider.addDataDisplay(cellList);

        // Create a SimplePager.
        SimplePager pager = new SimplePager();

        // Set the cellList as the display.
        pager.setDisplay(cellList);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(pager);
        vPanel.add(cellList);


        popupPanel.add(vPanel);

        popupPanel.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                int left = (Window.getClientWidth() - offsetWidth) / 3;
                int top = (Window.getClientHeight() - offsetHeight) / 3;
                popupPanel.setPopupPosition(left, top);
            }
        });
    }

    @Override
    public void destroy() {
        initializedPanel.removeFromParent();
    }
}

