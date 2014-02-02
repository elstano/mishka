package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Operation;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class OperationLoader implements ExcelLoader<Operation> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastHouseLoader.class);

    @Override
    public List<Operation> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Operation> operations = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingMachineOperationIdCell = row.getCell(0);
            if (castingMachineOperationIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingMachineOperationIdCell.getCellType()) {
                continue;
            }

            final int castingMachineOperationId = Double.valueOf(castingMachineOperationIdCell.getNumericCellValue()).intValue();
            final String type = ExcelUtil.getStringCellValue(row, 1);

            Operation operation = new Operation();
            operation.setId(castingMachineOperationId);
            operation.setType(type);

            operations.add(operation);
        }

        return operations;
    }
}
