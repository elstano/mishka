package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Mark;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class MarkLoader implements ExcelLoader<Mark> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkLoader.class);

    @Override
    public List<Mark> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Mark> marks = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell markIdCell = row.getCell(0);
            if (markIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != markIdCell.getCellType()) {
                continue;
            }

            final int markId = Double.valueOf(markIdCell.getNumericCellValue()).intValue();
            final String markName = ExcelUtil.getStringCellValue(row, 1);
            final Integer parentMarkId = ExcelUtil.getIntegerCellValue(row, 2);

            Mark mark = new Mark();
            mark.setId(markId);
            mark.setName(markName);
            mark.setParentMarkId(parentMarkId);

            marks.add(mark);
        }

        return marks;
    }
}
