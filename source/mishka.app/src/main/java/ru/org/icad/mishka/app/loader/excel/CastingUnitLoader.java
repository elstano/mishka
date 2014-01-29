package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastHouse;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.Mark;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class CastingUnitLoader implements ExcelLoader<CastingUnit> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitLoader.class);

    @Override
    public List<CastingUnit> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnit> castingUnits = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitIdCell = row.getCell(0);
            if (castingUnitIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitIdCell.getCellType()) {
                continue;
            }

            final int castingUnitId = Double.valueOf(castingUnitIdCell.getNumericCellValue()).intValue();
            final int castHouseId = ExcelUtil.getIntCellValue(row, 1);
            final Date date = ExcelUtil.getDateCellValue(row, 2);
            final int previousProductId = ExcelUtil.getIntCellValue(row, 3);
            final int ladlePourTimeMax = ExcelUtil.getIntCellValue(row, 4);
            final BigDecimal cleanCost = ExcelUtil.getBigDecimalCellValue(row, 5);
            final double feDecrease = ExcelUtil.getDoubleCellValue(row, 6);
            final double siDecrease = ExcelUtil.getDoubleCellValue(row, 7);
            final double cuDecrease = ExcelUtil.getDoubleCellValue(row, 8);
            final double mgDecrease = ExcelUtil.getDoubleCellValue(row, 9);
            final double mnDecrease = ExcelUtil.getDoubleCellValue(row, 10);
            final double tiDecrease = ExcelUtil.getDoubleCellValue(row, 11);

            CastingUnit castingUnit = new CastingUnit();
            castingUnit.setId(castingUnitId);
            castingUnit.setCastHouse(new CastHouse(castHouseId));
            castingUnit.setStartTime(date);
            castingUnit.setPreviousProductId(previousProductId);
            castingUnit.setLadlePourTimeMax(ladlePourTimeMax);
            castingUnit.setCleanCost(cleanCost);
            castingUnit.setFeDecrease(feDecrease);
            castingUnit.setSiDecrease(siDecrease);
            castingUnit.setCuDecrease(cuDecrease);
            castingUnit.setMgDecrease(mgDecrease);
            castingUnit.setMnDecrease(mnDecrease);
            castingUnit.setTiDecrease(tiDecrease);

            castingUnits.add(castingUnit);
        }

        return castingUnits;
    }
}
