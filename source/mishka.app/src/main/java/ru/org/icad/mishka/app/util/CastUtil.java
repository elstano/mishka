package ru.org.icad.mishka.app.util;

import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CustomerOrder;

public class CastUtil {

    private CastUtil() {
    }

    public static int getLengthBlank(Cast cast) {
        final CustomerOrder customerOrder = cast.getCustomerOrder();

        return cast.getIngotInBlankCount() * customerOrder.getLength() + customerOrder.getProduct().getClipping();
    }

    public static int getSlabsVolume(Cast cast)   {
        return (int) (cast.getIngotCount() * getLengthBlank(cast) * cast.getCustomerOrder().getHeight() * cast.getCustomerOrder().getWidth() * 2.741 * Math.pow(10, 9));

    }

    public static int getTbarsVolume(Cast cast) {
        return cast.getIngotCount() * getLengthBlank(cast);
    }

    public static int getBillitsVolume(Cast cast) {
        return (int) (cast.getIngotCount() * getLengthBlank(cast) * Math.PI / 4 * Math.sqrt(cast.getCustomerOrder().getDiameter()) * 2.741 * Math.pow(10, 9));
}

    public static int getSlabsCobVolume(Cast cast) {
        return getSlabsVolume(cast) * cast.getCustomerOrder().getProduct().getCob();
    }

    public static int getTbarsCobVolume(Cast cast) {
        return getTbarsVolume(cast) * cast.getCustomerOrder().getProduct().getCob();
    }

    public static int getBillitsCobVolume(Cast cast) {
        return getBillitsVolume(cast) * cast.getCustomerOrder().getProduct().getCob();
    }
}
