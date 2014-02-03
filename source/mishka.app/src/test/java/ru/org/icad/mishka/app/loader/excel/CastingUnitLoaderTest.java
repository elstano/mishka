package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CastingUnit;

import java.net.SocketPermission;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CastingUnitLoaderTest {
    @Test
    public void testLoad() throws Exception {
        URL url = CastingUnitLoaderTest.class.getResource("/init/dictionaries/excelTables.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        CastingUnitLoader castingUnitLoader = new CastingUnitLoader();
        List<CastingUnit> casts = castingUnitLoader.load(filePath, TableName.CASTING_UNIT);

        assertTrue(casts.size() == 33);
    }
}
