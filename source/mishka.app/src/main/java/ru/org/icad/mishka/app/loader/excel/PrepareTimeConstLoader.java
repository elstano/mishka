package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.model.CastingUnitCollector;
import ru.org.icad.mishka.app.model.CastingUnitDistributor;
import ru.org.icad.mishka.app.model.PrepareTimeConst;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class PrepareTimeConstLoader implements ExcelLoader<PrepareTimeConst> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareTimeConstLoader.class);

    @Override
    public List<PrepareTimeConst> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<PrepareTimeConst> prepareTimeConsts = Lists.newArrayList();

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
            final Integer castingUnitDistributor = ExcelUtil.getIntegerCellValue(row, 0);
            final Integer castingUnitCastingMachine = ExcelUtil.getIntegerCellValue(row, 0);
            final int mark = ExcelUtil.getIntegerCellValue(row, 0);
            final int durationTime = ExcelUtil.getIntegerCellValue(row, 0);


            PrepareTimeConst prepareTimeConst = new PrepareTimeConst();
            if (castingUnitCollectorId != null) {
                prepareTimeConst.setCastingUnitCollector(new CastingUnitCollector(castingUnitCollectorId));
            }
            if (castingUnitDistributor != null) {
                prepareTimeConst.setCastingUnitDistributor(new CastingUnitDistributor(castingUnitDistributor));
            }
            if (castingUnitCastingMachine != null) {
                prepareTimeConst.setCastingUnitCastingMachine(new CastingUnitCastingMachine(castingUnitCastingMachine));
            }

//            prepareTimeConst.setMark();
            prepareTimeConst.setDurationTime(durationTime);

            prepareTimeConsts.add(prepareTimeConst);
        }

        return prepareTimeConsts;
    }
}
