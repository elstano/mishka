package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastHouse;
import ru.org.icad.mishka.app.model.Filter;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class FilterLoader implements ExcelLoader<Filter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterLoader.class);

    @Override
    public List<Filter> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Filter> filters = Lists.newArrayList();

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
            final int castHouseId = ExcelUtil.getIntCellValue(row, 1);
            final int state = ExcelUtil.getIntCellValue(row, 2);
            final int startCastingUnitId = ExcelUtil.getIntCellValue(row, 3);
            final int startHeaterId = ExcelUtil.getIntCellValue(row, 4);
            final Date startTime = ExcelUtil.getDateCellValue(row, 5);
            final int resource = ExcelUtil.getIntCellValue(row, 6);
            final int resourceOver = ExcelUtil.getIntCellValue(row, 7);
            final int heatTime = ExcelUtil.getIntCellValue(row, 8);
            final int installTime = ExcelUtil.getIntCellValue(row, 9);

            Filter filter = new Filter();
            filter.setId(filterId);
            filter.setCastHouse(new CastHouse(castHouseId));
            filter.setState(state);
            filter.setStartCastingUnitId(startCastingUnitId);
            filter.setStartHeaterId(startHeaterId);
            filter.setStartTime(startTime);
            filter.setResource(resource);
            filter.setResourceOver(resourceOver);
            filter.setHeatTime(heatTime);
            filter.setInstallTime(installTime);

            filters.add(filter);
        }

        return filters;
    }
}
