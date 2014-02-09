package ru.org.icad.mishka.app.process.cob;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.loader.excel.CastLoader;
import ru.org.icad.mishka.app.loader.excel.ElectrolizerPrognosisLoader;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;


public class RestrictionByCobTest {


    @Test
    public void testCheckRestriction() throws UnsupportedEncodingException {
        URL urlCast = RestrictionByCobTest.class.getResource("/последовательность ходок.xlsx");
        String castFilePath = URLDecoder.decode(urlCast.getPath(), "UTF-8");

        CastLoader castLoader = new CastLoader();
        List<Cast> casts = castLoader.load(castFilePath, "CAST");

        URL urlEletrolizer = RestrictionByCobTest.class.getResource("/init/dictionaries/excelTables.xlsx");
        String eletrolizerFilePath = URLDecoder.decode(urlEletrolizer.getPath(), "UTF-8");

        ElectrolizerPrognosisLoader electrolizerPrognosisLoader = new ElectrolizerPrognosisLoader();
        List<ElectrolizerPrognosis> electrolizerPrognosises
                = electrolizerPrognosisLoader.load(eletrolizerFilePath, "ELECTROLIZER_PROGNOSIS");

        Map<Cast, List<ElectrolizerPrognosis>> castListMap =  RestrictionByCob.checkRestriction(casts, electrolizerPrognosises);

        assertTrue(castListMap.size() == 10);
    }

}
