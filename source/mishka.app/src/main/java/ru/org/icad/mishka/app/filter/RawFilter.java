package ru.org.icad.mishka.app.filter;

import ru.org.icad.mishka.app.chemistry.Chemistry;
import ru.org.icad.mishka.app.equipment.Electrolyzer;
import ru.org.icad.mishka.app.order.Order;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

@Deprecated
public class RawFilter {

    private RawFilter() {
    }

    public static Map<Order, List<Electrolyzer>> checkRestriction(List<Order> orders, List<Electrolyzer> electrolyzers) {
        final Map<Order, List<Electrolyzer>> resultOrderMap = Maps.newHashMap();
        final List<Electrolyzer> usedElectrolyzer = Lists.newArrayList();

        Collections.sort(electrolyzers, ELECTROLYZER_COMPARATOR);

        final Map<Electrolyzer, Order> electrolyzerMap = getElectolyzerMap(orders, electrolyzers);

        final Map<Order, List<Electrolyzer>> tempOrderMap = getOrderMap(orders, electrolyzers);
        final Map<Order, List<Electrolyzer>> currentOrderMap = sortByElectrolyzerCapacity(tempOrderMap);


        for (Map.Entry<Order, List<Electrolyzer>> entry : currentOrderMap.entrySet()) {
            Order currentOrder = entry.getKey();
            int orderCapacity = currentOrder.getCapacity();
            int electrolyzersCapacity = 0;

            List<Electrolyzer> electrolyzerList = entry.getValue();
            electrolyzerList.removeAll(usedElectrolyzer);

            Integer lastShitft = null;
            for (Electrolyzer electrolyzer : electrolyzerList) {
                if (orderCapacity <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                final Order order = electrolyzerMap.get(electrolyzer);
                if (currentOrder.equals(order)) {
                    electrolyzersCapacity += electrolyzer.getCapacity();
                    usedElectrolyzer.add(electrolyzer);

                    lastShitft = electrolyzer.getShift();
                }
            }

            electrolyzerList.removeAll(usedElectrolyzer);

            for (Electrolyzer electrolyzer : electrolyzerList) {
                if (orderCapacity <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                electrolyzersCapacity += electrolyzer.getCapacity();
                usedElectrolyzer.add(electrolyzer);

                lastShitft = electrolyzer.getShift();

            }

            if (electrolyzersCapacity >= orderCapacity) {
                resultOrderMap.put(currentOrder, usedElectrolyzer);
            }
        }


        return resultOrderMap;
    }

    private static boolean isNotLastShift(Integer lastShitft, Electrolyzer electrolyzer) {
        return lastShitft != null && lastShitft != electrolyzer.getShift();
    }

    private static Map<Order, List<Electrolyzer>> getOrderMap(List<Order> orders, List<Electrolyzer> electrolyzers) {
        final Map<Order, List<Electrolyzer>> tempOrderMap = Maps.newHashMap();

        for (Order order : orders) {
            List<Electrolyzer> suitableElectrolyzers = Lists.newArrayList();
            final Chemistry orderChemistry = order.getChemistry();

            for (Electrolyzer electrolyzer : electrolyzers) {
                if (orderChemistry.isSuit(electrolyzer.getChemistry())) {
                    suitableElectrolyzers.add(electrolyzer);
                }
            }

            if(suitableElectrolyzers.isEmpty()) {
                continue;
            }

            tempOrderMap.put(order, suitableElectrolyzers);
        }
        return tempOrderMap;
    }

    private static Map<Electrolyzer, Order> getElectolyzerMap(List<Order> orders, List<Electrolyzer> electrolyzers) {
        final Map<Electrolyzer, List<Order>> tempElectrolyzerMap = Maps.newHashMap();

        for (Electrolyzer electrolyzer : electrolyzers) {
            List<Order> suitableOrders = Lists.newArrayList();
            final Chemistry electrolyzerChemistry = electrolyzer.getChemistry();

            for (Order order : orders) {
                if (!electrolyzerChemistry.isSuit(order.getChemistry())) {
                    suitableOrders.add(order);
                }
            }

            if(suitableOrders.isEmpty()) {
                continue;
            }

            tempElectrolyzerMap.put(electrolyzer, suitableOrders);
        }

        final Map<Electrolyzer, Order> electrolyzerMap = Maps.newHashMap();

        for (Map.Entry<Electrolyzer, List<Order>> entry : tempElectrolyzerMap.entrySet()) {
            List<Order> orderList = entry.getValue();
            if (orderList.size() == 1) {
                electrolyzerMap.put(entry.getKey(), orderList.iterator().next());
            }
        }

        return electrolyzerMap;
    }


    private static Map<Order, List<Electrolyzer>> sortByElectrolyzerCapacity(Map<Order, List<Electrolyzer>> unsortMap) {
        final LinkedList<Map.Entry<Order, List<Electrolyzer>>> entries = Lists.newLinkedList(unsortMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Order, List<Electrolyzer>>>() {
            @Override
            public int compare(Map.Entry<Order, List<Electrolyzer>> o1, Map.Entry<Order, List<Electrolyzer>> o2) {
                int capacity1 = calculateCapacity(o1.getValue());
                int capacity2 = calculateCapacity(o2.getValue());
                if (capacity1 < capacity2) {
                    return 1;
                }
                if (capacity1 > capacity2) {
                    return -1;
                }

                return 0;
            }
        });

        final Map<Order, List<Electrolyzer>> sortedMap = Maps.newLinkedHashMap();
        for (Map.Entry<Order, List<Electrolyzer>> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private static int calculateCapacity(List<Electrolyzer> electrolyzers) {
        int capacity = 0;
        for (Electrolyzer electrolyzer : electrolyzers) {
            capacity += electrolyzer.getCapacity();
        }

        return capacity;
    }

    private static final Comparator<Electrolyzer> ELECTROLYZER_COMPARATOR = new Comparator<Electrolyzer>() {
        @Override
        public int compare(Electrolyzer o1, Electrolyzer o2) {
            if (o1.getShift() > o2.getShift()) {
                return 1;
            }

            if (o1.getShift() == o2.getShift()) {
                return 0;
            }

            return -1;
        }
    };

}
