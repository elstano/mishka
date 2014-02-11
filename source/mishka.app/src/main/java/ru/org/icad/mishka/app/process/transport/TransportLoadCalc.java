package ru.org.icad.mishka.app.process.transport;

import org.apache.commons.math3.util.Precision;
import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.util.CustomerOrderUtil;

import java.math.BigDecimal;

public class TransportLoadCalc {

    public OrderTransport calculateOrderTransport(CustomerOrder customerOrder) throws Exception {

        double ingotWeight = CustomerOrderUtil.getIngotWeight(customerOrder);

        Form form = customerOrder.getProduct().getForm();

        int transportCapacity = customerOrder.getTransportCapacity();

        if (Form.BILLET == form.getId() || Form.SLAB == form.getId()) {
            transportCapacity *= ingotWeight;
        }

        OrderTransport orderTransport = new OrderTransport();
        int orderTonnageMax = (customerOrder.getTolerancePlus() + 100) / 100 * customerOrder.getTonnage();

        int orderContainers = (int) Precision.round(orderTonnageMax / transportCapacity, BigDecimal.ROUND_UP);
        orderTransport.setOrderContainers(orderContainers);

        if (Form.BILLET == form.getId() || Form.SLAB == form.getId()) {
            orderTransport.setOrderIngots(orderTransport.getOrderContainers() * customerOrder.getTransportCapacity());
        }

        orderTransport.setOrderTonnage(orderTransport.getOrderContainers() * transportCapacity);

        return orderTransport;
    }
}
