package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CastingUnitDistributor;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitDistributorLoader implements ExcelLoader<CastingUnitDistributor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitDistributorLoader.class);

    @Override
    public List<CastingUnitDistributor> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitDistributor> castingUnitDistributors = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitDistributorIdCell = row.getCell(0);
            if (castingUnitDistributorIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitDistributorIdCell.getCellType()) {
                continue;
            }

            final int castingUnitDistributorId
                    = Double.valueOf(castingUnitDistributorIdCell.getNumericCellValue()).intValue();
            final int numCleans = ExcelUtil.getIntCellValue(row, 1);
            final int cleanTime = ExcelUtil.getIntCellValue(row, 2);

            CastingUnitDistributor castingUnitDistributor = new CastingUnitDistributor();
            castingUnitDistributor.setId(castingUnitDistributorId);
            castingUnitDistributor.setCastingUnit(new CastingUnit());
            castingUnitDistributor.setNumCleans(numCleans);
            castingUnitDistributor.setCleanTime(cleanTime);

            castingUnitDistributors.add(castingUnitDistributor);
        }

        return castingUnitDistributors;
    }
}
