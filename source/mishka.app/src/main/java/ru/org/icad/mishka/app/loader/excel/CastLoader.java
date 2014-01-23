package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.util.ExcelUtil;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.Order;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class CastLoader implements ExcelLoader<Cast> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastLoader.class);

    @Override
    public List<Cast> load(String filePath, String sheetName) {
        File file = new File(filePath);
        XSSFWorkbook workbook = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
        }

        if (workbook == null) {
            return Collections.emptyList();
        }

        XSSFSheet sheet = workbook.getSheet(sheetName);

        if(sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Cast> casts = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitIdCell = row.getCell(0);
            if (castingUnitIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitIdCell.getCellType()) {
                continue;
            }

            final int castingUnitId = Double.valueOf(castingUnitIdCell.getNumericCellValue()).intValue();
            CastingUnit castingUnit = new CastingUnit();
            castingUnit.setId(castingUnitId);

            Cell dateCell = row.getCell(1);
            final Date date = new Date(dateCell.getDateCellValue().getTime());

            final int shift = ExcelUtil.getIntCellValue(row, 2);
            final int castNumber = ExcelUtil.getIntCellValue(row, 3);
            final int orderId = ExcelUtil.getIntCellValue(row, 4);
            Order order = new Order();
            order.setId(orderId);

            final int ingotCount = ExcelUtil.getIntCellValue(row, 5);
            final int ingotInBlankCount = ExcelUtil.getIntCellValue(row, 6);


            Cast cast = new Cast();
            cast.setCastingUnit(castingUnit);
            cast.setDate(date);
            cast.setShift(shift);
            cast.setCastNumber(castNumber);
            cast.setOrder(order);
            cast.setIngotCount(ingotCount);
            cast.setIngotInBlankCount(ingotInBlankCount);

            casts.add(cast);
        }

        return casts;
    }


}
