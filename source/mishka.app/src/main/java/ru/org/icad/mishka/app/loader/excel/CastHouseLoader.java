package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastHouse;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastHouseLoader implements ExcelLoader<CastHouse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastHouseLoader.class);

    @Override
    public List<CastHouse> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastHouse> castHouses = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castHouseIdCell = row.getCell(0);
            if (castHouseIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castHouseIdCell.getCellType()) {
                continue;
            }

            final int castHouseId = Double.valueOf(castHouseIdCell.getNumericCellValue()).intValue();
            final int plantId = ExcelUtil.getIntCellValue(row, 1);
            final int ladleTonnageMax = ExcelUtil.getIntCellValue(row, 4);
            final int blankWeightMax = ExcelUtil.getIntCellValue(row, 5);

            CastHouse castHouse = new CastHouse();
            castHouse.setId(castHouseId);
            castHouse.setPlant(new Plant(plantId));
            castHouse.setLadleTonnageMax(ladleTonnageMax);
            castHouse.setBlankWeightMax(blankWeightMax);

            castHouses.add(castHouse);
        }

        return castHouses;
    }
}

