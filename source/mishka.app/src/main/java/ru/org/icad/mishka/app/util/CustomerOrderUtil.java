package ru.org.icad.mishka.app.util;

import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Form;

public class CustomerOrderUtil {

    private static final double RO = 2.741 * Math.pow(10, -9);

    private CustomerOrderUtil() {
    }

    public static double getIngotWeight(CustomerOrder customerOrder) throws Exception {
        final int formId = customerOrder.getProduct().getForm().getId();

        if (Form.SLAB == formId) {
            return customerOrder.getLength() * customerOrder.getHeight() * customerOrder.getWidth() * RO;
        }

        if (Form.BILLET == formId) {
            return customerOrder.getLength() * Math.PI / 4 * Math.pow(customerOrder.getDiameter(), 2) * RO;
        }

        if (Form.INGOT == formId) {
            return customerOrder.getWeight();
        }

        throw new Exception();
    }
}
