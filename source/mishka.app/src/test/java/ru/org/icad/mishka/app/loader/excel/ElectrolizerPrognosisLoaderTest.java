package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ElectrolizerPrognosisLoaderTest {
    @Test
    public void testLoad() throws Exception {
        URL url = ElectrolizerPrognosisLoaderTest.class.getResource("/init/dictionaries/excelTables.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        ElectrolizerPrognosisLoader electrolizerPrognosisLoader = new ElectrolizerPrognosisLoader();
        List<ElectrolizerPrognosis> electrolizerPrognosises
                = electrolizerPrognosisLoader.load(filePath, "ELECTROLIZER_PROGNOSIS");

        assertTrue(electrolizerPrognosises.size() == 101107);
    }
}
