package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitCastingMachineLoader implements ExcelLoader<CastingUnitCastingMachine> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitCastingMachineLoader.class);

    @Override
    public List<CastingUnitCastingMachine> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitCastingMachine> castingUnitCastingMachines = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitCastingMachineIdCell = row.getCell(0);
            if (castingUnitCastingMachineIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitCastingMachineIdCell.getCellType()) {
                continue;
            }

            final int castingUnitCastingMachineId
                    = Double.valueOf(castingUnitCastingMachineIdCell.getNumericCellValue()).intValue();
            final int castingUnitId = ExcelUtil.getIntegerCellValue(row, 1);
            final int remouldTime = ExcelUtil.getIntegerCellValue(row, 2);
            final int lenghtBlankMax = ExcelUtil.getIntegerCellValue(row, 3);

            CastingUnitCastingMachine castingUnitCastingMachine = new CastingUnitCastingMachine();
            castingUnitCastingMachine.setId(castingUnitCastingMachineId);
            castingUnitCastingMachine.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitCastingMachine.setRemouldTime(remouldTime);
            castingUnitCastingMachine.setLenghtBlankMax(lenghtBlankMax);

            castingUnitCastingMachines.add(castingUnitCastingMachine);
        }

        return castingUnitCastingMachines;
    }
}
