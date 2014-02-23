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

public class CastingUnitRepairLoader implements ExcelLoader<CastingUnitRepair> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitRepairLoader.class);

    @Override
    public List<CastingUnitRepair> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitRepair> castingUnitRepairs = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            if (row.getRowNum() == 0) {
                continue;
            }

            final Integer castingUnitCollectorId = ExcelUtil.getIntegerCellValue(row, 0);
            final Integer castingUnitDistributorId = ExcelUtil.getIntegerCellValue(row, 1);
            final Integer castingUnitCastingMachineId = ExcelUtil.getIntegerCellValue(row, 2);
            final Integer castingUnitHomogenCuttingLineId = ExcelUtil.getIntegerCellValue(row, 3);
            final Date timeStart = ExcelUtil.getDateCellValue(row, 4);
            final Date timeEnd = ExcelUtil.getDateCellValue(row, 5);

            CastingUnitRepair castingUnitRepair = new CastingUnitRepair();
            if (castingUnitCollectorId != null) {
                castingUnitRepair.setCastingUnitCollector(new CastingUnitCollector(castingUnitCollectorId));
            }
            if (castingUnitDistributorId != null) {
                castingUnitRepair.setCastingUnitDistributor(new CastingUnitDistributor(castingUnitDistributorId));
            }
            if (castingUnitCastingMachineId != null) {
                castingUnitRepair.setCastingUnitCastingMachine(new CastingUnitCastingMachine(castingUnitCastingMachineId));
            }
            if (castingUnitHomogenCuttingLineId != null) {
                castingUnitRepair.setCastingUnitHomogenCuttingLine(new CastingUnitHomogenCuttingLine(castingUnitHomogenCuttingLineId));
            }
            castingUnitRepair.setTimeStart(timeStart);
            castingUnitRepair.setTimeEnd(timeEnd);

            castingUnitRepairs.add(castingUnitRepair);
        }

        return castingUnitRepairs;
    }
}
