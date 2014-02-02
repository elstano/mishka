package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.ContainerType;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.model.TransportLoad;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class TransportLoadLoader implements ExcelLoader<TransportLoad> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportLoad.class);

    @Override
    public List<TransportLoad> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<TransportLoad> transportLoads = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell plantIdCell = row.getCell(0);
            if (plantIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != plantIdCell.getCellType()) {
                continue;
            }

            final int plantId = Double.valueOf(plantIdCell.getNumericCellValue()).intValue();
            final int containerTypeId = ExcelUtil.getIntegerCellValue(row, 2);
            final int formId = ExcelUtil.getIntegerCellValue(row, 3);
            final int diameter = ExcelUtil.getIntegerCellValue(row, 4);
            final int width = ExcelUtil.getIntegerCellValue(row, 5);
            final int height = ExcelUtil.getIntegerCellValue(row, 6);
            final int weight = ExcelUtil.getIntegerCellValue(row, 7);
            final int lengthMin = ExcelUtil.getIntegerCellValue(row, 8);
            final int lengthMax = ExcelUtil.getIntegerCellValue(row, 9);
            final String scheme = ExcelUtil.getStringCellValue(row, 10);
            final int numIngotsMin = ExcelUtil.getIntegerCellValue(row, 11);
            final int numIngotsMax = ExcelUtil.getIntegerCellValue(row, 12);
            final int tonnageMin = ExcelUtil.getIntegerCellValue(row, 13);
            final int tonnageMax = ExcelUtil.getIntegerCellValue(row, 14);

            TransportLoad transportLoad = new TransportLoad();
            transportLoad.setPlant(new Plant(plantId));
            transportLoad.setContainerType(new ContainerType(containerTypeId));
            transportLoad.setForm(new Form(formId));
            transportLoad.setDiameter(diameter);
            transportLoad.setWidth(width);
            transportLoad.setHeight(height);
            transportLoad.setWeight(weight);
            transportLoad.setLengthMin(lengthMin);
            transportLoad.setLengthMax(lengthMax);
            transportLoad.setScheme(scheme);
            transportLoad.setNumIngotsMin(numIngotsMin);
            transportLoad.setNumIngotsMax(numIngotsMax);
            transportLoad.setTonnageMin(tonnageMin);
            transportLoad.setTonnageMax(tonnageMax);

            transportLoads.add(transportLoad);
        }

        return transportLoads;
    }

}
