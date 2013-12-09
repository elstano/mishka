package ru.org.icad.mishka.web.gwt.main.client.upload;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.menu.OptionPresenter;
import ru.org.icad.mishka.web.gwt.main.shared.upload.ImportStatus;
import ru.org.icad.mishka.web.gwt.main.shared.upload.UploadedFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Boss on 12/10/13.
 */
public class UploadPresenter implements OptionPresenter{
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
        layout = new FlexTable();
        lastUploadDate = new Label("Дата последней загрузки:");
        upload = new Button("Загрузить информацио " + uploadCaption);
        layout.setWidget(0,0,lastUploadDate);
        layout.setWidget(0,1, upload);
        uploaded = new FlexTable();
        layout.getFlexCellFormatter().setColSpan(1, 0, 2);
        layout.setWidget(1,0,uploaded);
        presentFiles(filesStub);
        MainView.instance.getMainLayout().add(layout);
    }

    private void presentFiles(List<UploadedFile> files){
        uploaded.removeAllRows();
        int i=1;
        uploaded.setText(0,0,"Имя Файла");
        uploaded.setText(0,1,"Дата Завершения Загрузки");
        uploaded.setText(0,2,"Статус");
        DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-YYYY HH:mm:ss");
        for(UploadedFile f: files){
            uploaded.setText(i, 0, f.getName());
            uploaded.setText(i, 1, dtf.format(f.getUploadedDate()));
            uploaded.setText(i, 2, f.getImportStatus().toString());
            i++;
        }
    }

    @Override
    public void destroy() {
        layout.removeFromParent();
    }
}
