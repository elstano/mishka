package ru.org.icad.mishka.app.excel.loader;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.entity.ElectrolizerPrognosis;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ElectrolizerPrognosisLoaderTest {
    @Test
    public void testLoad() throws Exception {
        URL url = ElectrolizerPrognosisLoaderTest.class.getResource("/init/dictionaries/sample.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
        List<ElectrolizerPrognosis> electrolizerPrognosises = ElectrolizerPrognosisLoader.load(filePath, "ELECTROLIZER_PROGNOSIS");

        assertTrue(electrolizerPrognosises.size() == 20);
    }
}
