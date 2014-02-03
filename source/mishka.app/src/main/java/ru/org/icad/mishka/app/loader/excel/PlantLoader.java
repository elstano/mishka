package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.util.ExcelUtil;
import ru.org.icad.mishka.app.model.Plant;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class PlantLoader implements ExcelLoader<Plant> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantLoader.class);

    @Override
    public List<Plant> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Plant> plants = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell plantIdCell = row.getCell(0);
            if (plantIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != plantIdCell.getCellType()) {
                continue;
            }

            final int plantId = Double.valueOf(plantIdCell.getNumericCellValue()).intValue();
            final String plantName = ExcelUtil.getStringCellValue(row, 1);
            final BigDecimal premiumA7 = ExcelUtil.getBigDecimalCellValue(row, 2);
            final BigDecimal clipAddCost= ExcelUtil.getBigDecimalCellValue(row, 3);
            final BigDecimal clipMeltLoss= ExcelUtil.getBigDecimalCellValue(row, 4);

            Plant plant = new Plant();
            plant.setId(plantId);
            plant.setName(plantName);
            plant.setPremiumA7(premiumA7);
            plant.setAddCost(clipAddCost);
            plant.setMeltLoss(clipMeltLoss);

            plants.add(plant);
        }

        return plants;
    }
}
