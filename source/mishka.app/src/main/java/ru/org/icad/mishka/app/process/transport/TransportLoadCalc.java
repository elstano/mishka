package ru.org.icad.mishka.app.process.transport;

import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Form;

public class TransportLoadCalc {

    public OrderTransport calculateOrderTransport(CustomerOrder customerOrder) {

        int ingotWeight = 10;

        Form form = customerOrder.getProduct().getForm();

        int transportCapacity = customerOrder.getTransportCapacity();

        if (Form.BILLET == form.getId() || Form.SLAB == form.getId()) {
            transportCapacity *= ingotWeight;
        }

        OrderTransport orderTransport = new OrderTransport();
        int orderTonnageMax = (customerOrder.getTolerancePlus() + 100) / 100 * customerOrder.getTonnage();

        // ToDo Обрезать остаток от деления
        orderTransport.setOrderContainers(orderTonnageMax / transportCapacity);

        if ("BILLET".equals(form.getName()) || "SLAB".equals(form.getName())) {
            orderTransport.setOrderIngots(orderTransport.getOrderContainers() * customerOrder.getTransportCapacity());
        }

        orderTransport.setOrderTonnage(orderTransport.getOrderContainers() * transportCapacity);

        return orderTransport;
    }
}
