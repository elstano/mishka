package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Filter;
import ru.org.icad.mishka.app.model.FilterChangeMark;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class FilterChangeMarkLoader implements ExcelLoader<FilterChangeMark> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterChangeMarkLoader.class);

    @Override
    public List<FilterChangeMark> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<FilterChangeMark> filterChangeMarks = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell filterIdCell = row.getCell(0);
            if (filterIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != filterIdCell.getCellType()) {
                continue;
            }

            final int filterId = Double.valueOf(filterIdCell.getNumericCellValue()).intValue();
            final int markId1 = ExcelUtil.getIntCellValue(row, 1);
            final int markId2 = ExcelUtil.getIntCellValue(row, 2);
            final int durationTime = ExcelUtil.getIntCellValue(row, 3);

            FilterChangeMark filterChangeMark = new FilterChangeMark();
            filterChangeMark.setFilter(new Filter(filterId));
            filterChangeMark.setMarkId1(markId1);
            filterChangeMark.setMarkId2(markId2);
            filterChangeMark.setDurationTime(durationTime);

            filterChangeMarks.add(filterChangeMark);
        }

        return filterChangeMarks;
    }
}
