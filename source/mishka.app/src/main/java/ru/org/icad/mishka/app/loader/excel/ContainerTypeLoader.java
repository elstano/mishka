package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.ContainerType;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class ContainerTypeLoader implements ExcelLoader<ContainerType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerTypeLoader.class);

    @Override
    public List<ContainerType> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<ContainerType> containerTypes = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell containerTypeIdCell = row.getCell(0);
            if (containerTypeIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != containerTypeIdCell.getCellType()) {
                continue;
            }

            final int containerTypeId = Double.valueOf(containerTypeIdCell.getNumericCellValue()).intValue();
            final String name = ExcelUtil.getStringCellValue(row, 1);

            ContainerType containerType = new ContainerType();
            containerType.setId(containerTypeId);
            containerType.setName(name);

            containerTypes.add(containerType);
        }

        return containerTypes;
    }
}
