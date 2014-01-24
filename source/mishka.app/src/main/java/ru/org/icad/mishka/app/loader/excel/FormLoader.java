package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class FormLoader implements ExcelLoader<Form> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormLoader.class);

    @Override
    public List<Form> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Form> forms = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell formIdCell = row.getCell(0);
            if (formIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != formIdCell.getCellType()) {
                continue;
            }

            final int formId = Double.valueOf(formIdCell.getNumericCellValue()).intValue();
            final String formName = ExcelUtil.getStringCellValue(row, 1);


            Form form = new Form();
            form.setId(formId);
            form.setName(formName);

            forms.add(form);
        }

        return forms;
    }
}
