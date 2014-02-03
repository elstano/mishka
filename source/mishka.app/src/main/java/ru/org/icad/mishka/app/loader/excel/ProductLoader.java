package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class ProductLoader implements ExcelLoader<Product> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductLoader.class);

    @Override
    public List<Product> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Product> products = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell productIdCell = row.getCell(0);
            if (productIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != productIdCell.getCellType()) {
                continue;
            }

            final int productId = Double.valueOf(productIdCell.getNumericCellValue()).intValue();
            final String productName = ExcelUtil.getStringCellValue(row, 1);
            final int plantId = ExcelUtil.getIntegerCellValue(row, 2);
            final String spec = ExcelUtil.getStringCellValue(row, 3);
            final String status = ExcelUtil.getStringCellValue(row, 4);
            final String series = ExcelUtil.getStringCellValue(row, 5);
            final int markId = ExcelUtil.getIntegerCellValue(row, 6);
            final int formId = ExcelUtil.getIntegerCellValue(row, 7);
            final int filtrationId = ExcelUtil.getIntegerCellValue(row, 8);
            final String homogenization = ExcelUtil.getStringCellValue(row, 9);
            final int clipping = ExcelUtil.getIntegerCellValue(row, 10);
            final int cob = ExcelUtil.getIntegerCellValue(row, 11);
            final Double siMin = ExcelUtil.getDoubleCellValue(row, 12);
            final Double siMax = ExcelUtil.getDoubleCellValue(row, 13);
            final Double feMin = ExcelUtil.getDoubleCellValue(row, 14);
            final Double feMax = ExcelUtil.getDoubleCellValue(row, 15);
            final Double cuMin = ExcelUtil.getDoubleCellValue(row, 16);
            final Double cuMax = ExcelUtil.getDoubleCellValue(row, 17);
            final Double mgMin = ExcelUtil.getDoubleCellValue(row, 18);
            final Double mgMax = ExcelUtil.getDoubleCellValue(row, 19);
            final Double mnMin = ExcelUtil.getDoubleCellValue(row, 20);
            final Double mnMax = ExcelUtil.getDoubleCellValue(row, 21);
            final Double crMin = ExcelUtil.getDoubleCellValue(row, 22);
            final Double crMax = ExcelUtil.getDoubleCellValue(row, 23);
            final Double znMin = ExcelUtil.getDoubleCellValue(row, 24);
            final Double znMax = ExcelUtil.getDoubleCellValue(row, 25);
            final Double tiMin = ExcelUtil.getDoubleCellValue(row, 26);
            final Double tiMax = ExcelUtil.getDoubleCellValue(row, 27);
            final Double bMin = ExcelUtil.getDoubleCellValue(row, 28);
            final Double bMax = ExcelUtil.getDoubleCellValue(row, 29);
            final Double naMin = ExcelUtil.getDoubleCellValue(row, 30);
            final Double naMax = ExcelUtil.getDoubleCellValue(row, 31);
            final Double caMin = ExcelUtil.getDoubleCellValue(row, 32);
            final Double caMax = ExcelUtil.getDoubleCellValue(row, 33);

            Product product = new Product();
            product.setId(productId);
            product.setName(productName);
            product.setPlant(new Plant(plantId));
            product.setSpec(spec);
            product.setSeries(series);
            product.setMark(new Mark(markId));
            product.setForm(new Form(formId));
            product.setFiltration(new Filtration(filtrationId));
            product.setHomogenization(homogenization);
            product.setClipping(clipping);
            product.setCob(cob);
            product.setSiMin(siMin);
            product.setSiMax(siMax);
            product.setFeMin(feMin);
            product.setFeMax(feMax);
            product.setCuMin(cuMin);
            product.setCuMax(cuMax);
            product.setMgMin(mgMin);
            product.setMgMax(mgMax);
            product.setMnMin(mnMin);
            product.setMnMax(mnMax);
            product.setCrMin(crMin);
            product.setCrMax(crMax);
            product.setZnMin(znMin);
            product.setZnMax(znMax);
            product.setTiMin(tiMin);
            product.setTiMax(tiMax);
            product.setBMin(bMin);
            product.setBMax(bMax);
            product.setNaMin(naMin);
            product.setNaMax(naMax);
            product.setCaMin(caMin);
            product.setCaMax(caMax);

            products.add(product);
        }

        return products;
    }
}

