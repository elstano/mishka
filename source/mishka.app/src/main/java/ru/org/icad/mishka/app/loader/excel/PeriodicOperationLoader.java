package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class PeriodicOperationLoader implements ExcelLoader<PeriodicOperation> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicOperationLoader.class);

    @Override
    public List<PeriodicOperation> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<PeriodicOperation> periodicOperations = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {

                continue;
            }

            Cell orderIdCell = row.getCell(0);
            if (orderIdCell == null) {
                continue;
            }

            if (row.getRowNum() == 0) {
                continue;
            }

            final Integer castingUnitCollectorId = ExcelUtil.getIntegerCellValue(row, 0);
            final Integer castingUnitDistributorId = ExcelUtil.getIntegerCellValue(row, 1);
            final Integer castingUnitCastingMachineId = ExcelUtil.getIntegerCellValue(row, 2);
            final int operationId = ExcelUtil.getIntegerCellValue(row, 3);
            final Date operationDate = ExcelUtil.getDateCellValue(row, 4);
            final int shift = ExcelUtil.getIntegerCellValue(row, 5);
            final Date durationTime = ExcelUtil.getDateCellValue(row, 6);


            PeriodicOperation periodicOperation = new PeriodicOperation();
            if (castingUnitCollectorId != null) {
                periodicOperation.setCastingUnitCollector(new CastingUnitCollector(castingUnitCollectorId));
            }
            if (castingUnitDistributorId != null) {
                periodicOperation.setCastingUnitDistributor(new CastingUnitDistributor(castingUnitDistributorId));
            }
            if (castingUnitCastingMachineId != null) {
                periodicOperation.setCastingUnitCastingMachine(new CastingUnitCastingMachine(castingUnitCastingMachineId));
            }
            periodicOperation.setOperation(new Operation(operationId));
            periodicOperation.setOperationDate(operationDate);
            periodicOperation.setShift(shift);
            periodicOperation.setDurationTime(durationTime);

            periodicOperations.add(periodicOperation);
        }

        return periodicOperations;
    }
}
