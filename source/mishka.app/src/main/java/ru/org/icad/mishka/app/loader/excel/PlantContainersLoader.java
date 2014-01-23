package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.ContainerType;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.model.PlantContainers;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class PlantContainersLoader implements ExcelLoader<PlantContainers> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantContainersLoader.class);

    @Override
    public List<PlantContainers> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<PlantContainers> plantContainerses = Lists.newArrayList();

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
            final int plantId = ExcelUtil.getIntCellValue(row, 1);
            final int numContainers = ExcelUtil.getIntCellValue(row, 2);

            PlantContainers plantContainers = new PlantContainers();
            plantContainers.setContainerType(new ContainerType(containerTypeId));
            plantContainers.setPlant(new Plant(plantId));
            plantContainers.setNumContainers(numContainers);

            plantContainerses.add(plantContainers);
        }

        return plantContainerses;
    }
}
