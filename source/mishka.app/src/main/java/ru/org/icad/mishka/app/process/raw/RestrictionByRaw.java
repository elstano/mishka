package ru.org.icad.mishka.app.process.raw;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ru.org.icad.mishka.app.chemistry.Chemistry;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;
import ru.org.icad.mishka.app.order.Order;

import java.util.*;

public class RestrictionByRaw {

    private RestrictionByRaw() {
    }

    public static Map<Order, List<ElectrolizerPrognosis>> checkRestriction(List<Order> orders, List<ElectrolizerPrognosis> electrolyzers) {
        final Map<Order, List<ElectrolizerPrognosis>> resultOrderMap = Maps.newHashMap();
        final List<ElectrolizerPrognosis> usedElectrolyzer = Lists.newArrayList();

        Collections.sort(electrolyzers, ELECTROLYZER_COMPARATOR);

        final Map<ElectrolizerPrognosis, Order> electrolyzerMap = getElectolyzerMap(orders, electrolyzers);

        final Map<Order, List<ElectrolizerPrognosis>> tempOrderMap = getOrderMap(orders, electrolyzers);
        final Map<Order, List<ElectrolizerPrognosis>> currentOrderMap = sortByElectrolyzerCapacity(tempOrderMap);


        for (Map.Entry<Order, List<ElectrolizerPrognosis>> entry : currentOrderMap.entrySet()) {
            Order currentOrder = entry.getKey();
            int orderCapacity = currentOrder.getCapacity();
            int electrolyzersCapacity = 0;

            List<ElectrolizerPrognosis> electrolyzerList = entry.getValue();
            electrolyzerList.removeAll(usedElectrolyzer);

            Integer lastShitft = null;
            for (ElectrolizerPrognosis electrolyzer : electrolyzerList) {
                if (orderCapacity <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                final Order order = electrolyzerMap.get(electrolyzer);
                if (currentOrder.equals(order)) {
                    electrolyzersCapacity += electrolyzer.getTonnage();
                    usedElectrolyzer.add(electrolyzer);

                    lastShitft = electrolyzer.getShift();
                }
            }

            electrolyzerList.removeAll(usedElectrolyzer);

            for (ElectrolizerPrognosis electrolyzer : electrolyzerList) {
                if (orderCapacity <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                electrolyzersCapacity += electrolyzer.getTonnage();
                usedElectrolyzer.add(electrolyzer);

                lastShitft = electrolyzer.getShift();

            }

            if (electrolyzersCapacity >= orderCapacity) {
                resultOrderMap.put(currentOrder, usedElectrolyzer);
            }
        }


        return resultOrderMap;
    }

    private static boolean isNotLastShift(Integer lastShitft, ElectrolizerPrognosis electrolyzer) {
        return lastShitft != null && lastShitft != electrolyzer.getShift();
    }

    private static Map<Order, List<ElectrolizerPrognosis>> getOrderMap(List<Order> orders, List<ElectrolizerPrognosis> electrolyzers) {
        final Map<Order, List<ElectrolizerPrognosis>> tempOrderMap = Maps.newHashMap();

        for (Order order : orders) {
            List<ElectrolizerPrognosis> suitableElectrolyzers = Lists.newArrayList();
            final Chemistry orderChemistry = order.getChemistry();

//            for (ElectrolizerPrognosis electrolyzer : electrolyzers) {
//                if (orderChemistry.isSuit(electrolyzer.getChemistry())) {
//                    suitableElectrolyzers.add(electrolyzer);
//                }
//            }

            if (suitableElectrolyzers.isEmpty()) {
                continue;
            }

            tempOrderMap.put(order, suitableElectrolyzers);
        }
        return tempOrderMap;
    }

    private static Map<ElectrolizerPrognosis, Order> getElectolyzerMap(List<Order> orders, List<ElectrolizerPrognosis> electrolyzers) {
        final Map<ElectrolizerPrognosis, List<Order>> tempElectrolyzerMap = Maps.newHashMap();

        for (ElectrolizerPrognosis electrolyzer : electrolyzers) {
            List<Order> suitableOrders = Lists.newArrayList();
//            final Chemistry electrolyzerChemistry = electrolyzer.getChemistry();
//
//            for (Order order : orders) {
//                if (!electrolyzerChemistry.isSuit(order.getChemistry())) {
//                    suitableOrders.add(order);
//                }
//            }

            if (suitableOrders.isEmpty()) {
                continue;
            }

            tempElectrolyzerMap.put(electrolyzer, suitableOrders);
        }

        final Map<ElectrolizerPrognosis, Order> electrolyzerMap = Maps.newHashMap();

        for (Map.Entry<ElectrolizerPrognosis, List<Order>> entry : tempElectrolyzerMap.entrySet()) {
            List<Order> orderList = entry.getValue();
            if (orderList.size() == 1) {
                electrolyzerMap.put(entry.getKey(), orderList.iterator().next());
            }
        }

        return electrolyzerMap;
    }


    private static Map<Order, List<ElectrolizerPrognosis>> sortByElectrolyzerCapacity(Map<Order, List<ElectrolizerPrognosis>> unsortMap) {
        final LinkedList<Map.Entry<Order, List<ElectrolizerPrognosis>>> entries = Lists.newLinkedList(unsortMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Order, List<ElectrolizerPrognosis>>>() {
            @Override
            public int compare(Map.Entry<Order, List<ElectrolizerPrognosis>> o1, Map.Entry<Order, List<ElectrolizerPrognosis>> o2) {
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

        final Map<Order, List<ElectrolizerPrognosis>> sortedMap = Maps.newLinkedHashMap();
        for (Map.Entry<Order, List<ElectrolizerPrognosis>> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private static int calculateCapacity(List<ElectrolizerPrognosis> electrolyzers) {
        int capacity = 0;
        for (ElectrolizerPrognosis electrolyzer : electrolyzers) {
            capacity += electrolyzer.getTonnage();
        }

        return capacity;
    }

    private static final Comparator<ElectrolizerPrognosis> ELECTROLYZER_COMPARATOR = new Comparator<ElectrolizerPrognosis>() {
        @Override
        public int compare(ElectrolizerPrognosis o1, ElectrolizerPrognosis o2) {
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
