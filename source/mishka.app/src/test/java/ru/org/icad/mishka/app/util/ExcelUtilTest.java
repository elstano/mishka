package ru.org.icad.mishka.app.util;

import org.testng.annotations.Test;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ExcelUtilTest {

    @Test
    public void testGetSheetNames() throws Exception {
        URL url = ExcelUtilTest.class.getResource("/init/dictionaries/sample.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        List<String> sheetNames = ExcelUtil.getSheetNames(filePath);

        assertTrue(sheetNames.size() == 40);
    }
}
