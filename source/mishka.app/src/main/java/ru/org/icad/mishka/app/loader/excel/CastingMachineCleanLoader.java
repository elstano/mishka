package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingMachineClean;
import ru.org.icad.mishka.app.model.CastingMachineOperation;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class CastingMachineCleanLoader implements ExcelLoader<CastingMachineClean> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingMachineCleanLoader.class);

    @Override
    public List<CastingMachineClean> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingMachineClean> castingMachineCleans = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingMachineCleanIdCell = row.getCell(0);
            if (castingMachineCleanIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingMachineCleanIdCell.getCellType()) {
                continue;
            }

            final int castingMachineCleanId = Double.valueOf(castingMachineCleanIdCell.getNumericCellValue()).intValue();
            final int castingMachineOperationId = ExcelUtil.getIntCellValue(row, 1);

            Cell dateCell = row.getCell(2);
            final Date date = new Date(dateCell.getDateCellValue().getTime());
            final int shift = ExcelUtil.getIntCellValue(row, 3);

            CastingMachineClean castingMachineClean = new CastingMachineClean();
            castingMachineClean.setCastingUnitCastingMachine(new CastingUnitCastingMachine(castingMachineCleanId));
            castingMachineClean.setCastingMachineOperation(new CastingMachineOperation(castingMachineOperationId));
            castingMachineClean.setDate(date);
            castingMachineClean.setShift(shift);

            castingMachineCleans.add(castingMachineClean);
        }

        return castingMachineCleans;
    }
}
