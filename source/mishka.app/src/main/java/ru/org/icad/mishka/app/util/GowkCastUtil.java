package ru.org.icad.mishka.app.util;

import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.process.casting.CastWrapper;

public final class GowkCastUtil {
    private static final double RO = 2.741 * Math.pow(10, -9);


    private GowkCastUtil() {
    }

    public static int getLengthBlank(CastWrapper castWrapper) {
        Cast cast = castWrapper.getCast();

        final CustomerOrder customerOrder = cast.getCustomerOrder();

        int lengthOne = cast.getIngotInBlankCount() * customerOrder.getLength() + customerOrder.getProduct().getClipping();
        int lengthTwo = castWrapper.getIngotInBlankCountTwo() * castWrapper.getLengthTwo() + customerOrder.getProduct().getClipping();

        return Math.max(lengthOne, lengthTwo);
    }

    public static double getTonnage(CastWrapper castWrapper) throws Exception {
        Cast cast = castWrapper.getCast();

        final CustomerOrder customerOrder = cast.getCustomerOrder();
        final int formId = customerOrder.getProduct().getForm().getId();

        if (Form.SLAB == formId) {
            return (cast.getBlankCount() + castWrapper.getBlankCountTwo()) * getLengthBlank(castWrapper) * customerOrder.getHeight() * customerOrder.getWidth() * RO;
        }

        if (Form.BILLET == formId) {
            return (cast.getBlankCount() + castWrapper.getBlankCountTwo()) * getLengthBlank(castWrapper) * Math.PI / 4 * Math.pow(customerOrder.getDiameter(), 2) * RO;
        }

        if (Form.INGOT == formId) {
            return Math.floor((castWrapper.getCast().getBlankCount() * castWrapper.getCast().getIngotInBlankCount()
                    + castWrapper.getBlankCountTwo() * castWrapper.getIngotInBlankCountTwo())
                    * castWrapper.getCast().getCustomerOrder().getWeight()) / 1000;
        }

        throw new Exception();
    }

    public static double getCobTonnage(CastWrapper castWrapper) throws Exception {
        Cast cast = castWrapper.getCast();

        return getTonnage(castWrapper) * cast.getCustomerOrder().getProduct().getCob() / 1000;
    }
}
