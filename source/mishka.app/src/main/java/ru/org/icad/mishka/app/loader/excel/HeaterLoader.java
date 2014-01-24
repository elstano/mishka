package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastHouse;
import ru.org.icad.mishka.app.model.Heater;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class HeaterLoader implements ExcelLoader<Heater> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeaterLoader.class);

    @Override
    public List<Heater> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Heater> heaters = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell heaterIdCell = row.getCell(0);
            if (heaterIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != heaterIdCell.getCellType()) {
                continue;
            }

            final int heaterId = Double.valueOf(heaterIdCell.getNumericCellValue()).intValue();
            final int castHouseId = ExcelUtil.getIntCellValue(row, 1);
            final int state = ExcelUtil.getIntCellValue(row, 2);
            final Date startTime = ExcelUtil.getDateCellValue(row, 3);

            Heater heater = new Heater();
            heater.setId(heaterId);
            heater.setCastHouse(new CastHouse(castHouseId));
            heater.setState(state);
            heater.setStartTime(startTime);

            heaters.add(heater);
        }

        return heaters;
    }
}
