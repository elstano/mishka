package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.ContainerType;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.model.PlantContainer;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class PlantContainersLoader implements ExcelLoader<PlantContainer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantContainersLoader.class);

    @Override
    public List<PlantContainer> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<PlantContainer> plantContainerses = Lists.newArrayList();

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

            final int containerTypeId = ExcelUtil.getIntegerCellValue(row, 0);
            final int plantId = ExcelUtil.getIntegerCellValue(row, 1);
            final int numContainers = ExcelUtil.getIntegerCellValue(row, 2);

            PlantContainer plantContainer = new PlantContainer();
            plantContainer.setContainerType(new ContainerType(containerTypeId));
            plantContainer.setPlant(new Plant(plantId));
            plantContainer.setNumContainers(numContainers);

            plantContainerses.add(plantContainer);
        }

        return plantContainerses;
    }
}
