package ru.org.icad.mishka.web.gwt.main.client.upload;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.controller.ProgressController;
import ru.org.icad.mishka.web.gwt.main.client.menu.OptionPresenter;
import ru.org.icad.mishka.web.gwt.main.client.upload.theme.UploadBundle;
import ru.org.icad.mishka.web.gwt.main.client.upload.theme.UploadCss;
import ru.org.icad.mishka.web.gwt.main.client.view.UploadProgressView;
import ru.org.icad.mishka.web.gwt.main.shared.upload.ImportStatus;
import ru.org.icad.mishka.web.gwt.main.shared.upload.UploadedFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadPresenter implements OptionPresenter {

    private String uploadCaption;

    private FlexTable layout;

    private Label lastUploadDate;
    private Button upload;
    private FlexTable uploaded;

    private List<UploadedFile> filesStub = new ArrayList<UploadedFile>();

    {
        filesStub.add(new UploadedFile("file3", new Date(), ImportStatus.NOT_STARTED));
        filesStub.add(new UploadedFile("file2", new Date(100000), ImportStatus.IN_PROGRESS));
        filesStub.add(new UploadedFile("file1", new Date(0), ImportStatus.FINISHED));
    }

    public UploadPresenter(String uploadCaption) {
        this.uploadCaption = uploadCaption;
    }

    @Override
    public void presentOption() {
        UploadCss css = UploadBundle.INSTANCE.css();
        css.ensureInjected();

        layout = new FlexTable();
        layout.setStyleName(css.uploadMain());
        lastUploadDate = new Label("Дата последней загрузки:");
        upload = new Button("Загрузить информацио " + uploadCaption);
        upload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final MyPopup popup = new MyPopup();
                UploadProgressView uploadProgressView = new UploadProgressView();
                popup.setWidget(uploadProgressView);
                popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                    @Override
                    public void setPosition(int offsetWidth, int offsetHeight) {
                        int left = (Window.getClientWidth() - offsetWidth) / 3;
                        int top = (Window.getClientHeight() - offsetHeight) / 3;
                        popup.setPopupPosition(left, top);
                    }
                });
//                popup.addCloseHandler(new CloseHandler<PopupPanel>() {
//                    @Override
//                    public void onClose(CloseEvent<PopupPanel> popupPanelCloseEvent) {
//                        Window.alert("Close!");
//                    }
//                });
            }
        });

        layout.setWidget(0, 0, lastUploadDate);
        layout.getFlexCellFormatter().setStyleName(0, 0, css.lastUploadDate());
        layout.setWidget(0, 1, upload);
        layout.getFlexCellFormatter().setStyleName(0, 1, css.uploadButtonTD());
        uploaded = new FlexTable();
        layout.getFlexCellFormatter().setColSpan(1, 0, 2);
        layout.setWidget(1, 0, uploaded);

        presentFiles(filesStub);
        MainView.instance.getMainLayout().add(layout);
    }

    private void presentFiles(List<UploadedFile> files) {
        UploadCss css = UploadBundle.INSTANCE.css();
        css.ensureInjected();
        uploaded.removeAllRows();
        uploaded.setStyleName(css.uploadsTable());

        uploaded.setCellPadding(0);
        uploaded.setCellSpacing(0);

        int i = 1;
        uploaded.getRowFormatter().setStyleName(0, css.headerTR());
        uploaded.getColumnFormatter().setStyleName(0, css.iconCol());
        uploaded.getColumnFormatter().setStyleName(2, css.dateCol());
        uploaded.getColumnFormatter().setStyleName(3, css.statusCol());
        uploaded.setHTML(0, 0, "&nbsp;");
        uploaded.setText(0, 1, "Имя Файла");
        uploaded.setText(0, 2, "Дата Завершения Загрузки");
        uploaded.setText(0, 3, "Статус");
        DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-YYYY HH:mm:ss");
        for (UploadedFile f : files) {
            uploaded.setWidget(i, 0, new Image(UploadBundle.INSTANCE.excel()));
            uploaded.setText(i, 1, f.getName());
            uploaded.getFlexCellFormatter().setStyleName(i, 1, css.fileNameTD());
            uploaded.setText(i, 2, dtf.format(f.getUploadedDate()));
            uploaded.getFlexCellFormatter().setStyleName(i, 2, css.dateTD());
            uploaded.getFlexCellFormatter().setStyleName(i, 3, css.statusTD());
            switch (f.getImportStatus()) {
                case FINISHED:
                    uploaded.setWidget(i, 3, new Image(UploadBundle.INSTANCE.uploaded()));
                    break;
                case IN_PROGRESS:
                    uploaded.setWidget(i, 3, new Image(UploadBundle.INSTANCE.loading()));
                    break;
                case NOT_STARTED:
                    uploaded.setWidget(i, 3, new Image(UploadBundle.INSTANCE.pending()));
            }
            i++;
        }
    }

    @Override
    public void destroy() {
        layout.removeFromParent();
    }


    private static class MyPopup extends PopupPanel {

        public MyPopup() {
            super(true);
            setWidget(new Label("Click outside of this popup to close it"));
        }
    }
}

