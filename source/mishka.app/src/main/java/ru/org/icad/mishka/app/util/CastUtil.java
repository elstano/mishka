package ru.org.icad.mishka.app.util;

import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Form;

public final class CastUtil {

    public static final double RO = 2.741 * Math.pow(10, -9);

    private CastUtil() {
    }

    public static int getLengthBlank(Cast cast) {
        final CustomerOrder customerOrder = cast.getCustomerOrder();

        return cast.getIngotInBlankCount() * customerOrder.getLength() + customerOrder.getProduct().getClipping();
    }

    public static double getTonnage(Cast cast) throws Exception {
        final CustomerOrder customerOrder = cast.getCustomerOrder();
        final int formId = customerOrder.getProduct().getForm().getId();

        if (Form.SLAB == formId) {
            return RO * cast.getBlankCount() * getLengthBlank(cast) * customerOrder.getHeight() * customerOrder.getWidth();
        }

        if (Form.BILLET == formId) {
            return RO * cast.getBlankCount() * getLengthBlank(cast) * Math.PI / 4 * Math.pow(customerOrder.getDiameter(), 2);
        }

        if (Form.INGOT == formId) {
            return Math.floor(cast.getBlankCount() * cast.getIngotInBlankCount() * customerOrder.getWeight() / 1000);
        }

        throw new Exception();
    }

    public static double getCobTonnage(Cast cast) throws Exception {
        return getTonnage(cast) * cast.getCustomerOrder().getProduct().getCob() / 1000;
    }
}
