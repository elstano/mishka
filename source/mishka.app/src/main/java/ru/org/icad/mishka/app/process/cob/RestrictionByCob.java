package ru.org.icad.mishka.app.process.cob;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Cast;
import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.ElectrolizerPrognosis;
import ru.org.icad.mishka.app.model.Product;
import ru.org.icad.mishka.app.util.CastUtil;

import java.util.*;

public class RestrictionByCob {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestrictionByCob.class);

    private static final Comparator<ElectrolizerPrognosis> ELECTROLYZER_COMPARATOR = new Comparator<ElectrolizerPrognosis>() {
        @Override
        public int compare(ElectrolizerPrognosis o1, ElectrolizerPrognosis o2) {
            final int compareResult = o1.getPrognosDate().compareTo(o2.getPrognosDate());
            if (compareResult == 0) {
                if (o1.getShift() > o2.getShift()) {
                    return 1;
                }

                if (o1.getShift() == o2.getShift()) {
                    return 0;
                }

                return -1;

            }

            return compareResult;
        }
    };

    private RestrictionByCob() {
    }

    public static Map<Cast, List<ElectrolizerPrognosis>> checkRestriction(List<Cast> casts, List<ElectrolizerPrognosis> electrolyzers) {

        Map<ElectrolizerPrognosisKey, List<ElectrolizerPrognosis>> electrolizerPrognosisesMap = Maps.newHashMap();

        for (ElectrolizerPrognosis electrolizerPrognosis : electrolyzers) {
            ElectrolizerPrognosisKey electrolizerPrognosisKey = new ElectrolizerPrognosisKey(electrolizerPrognosis.getPrognosDate(), electrolizerPrognosis.getShift());

            final List<ElectrolizerPrognosis> electrolizerPrognosises = electrolizerPrognosisesMap.get(electrolizerPrognosisKey);
            if (electrolizerPrognosises == null) {
                electrolizerPrognosisesMap.put(electrolizerPrognosisKey, Lists.<ElectrolizerPrognosis>newArrayList(electrolizerPrognosis));

                continue;
            }

            electrolizerPrognosises.add(electrolizerPrognosis);
            electrolizerPrognosisesMap.put(electrolizerPrognosisKey, electrolizerPrognosises);
        }


        final Map<Cast, List<ElectrolizerPrognosis>> resultOrderMap = Maps.newHashMap();
        final List<ElectrolizerPrognosis> usedElectrolyzer = Lists.newArrayList();

        Collections.sort(electrolyzers, ELECTROLYZER_COMPARATOR);

        final Map<ElectrolizerPrognosis, Cast> electrolyzerMap = getElectolyzerMap(casts, electrolyzers);

        final Map<Cast, List<ElectrolizerPrognosis>> tempCastMap = getOrderMap(casts, electrolyzers);
        final Map<Cast, List<ElectrolizerPrognosis>> currentCastMap = sortByElectrolyzerCapacity(tempCastMap);

        for (Map.Entry<Cast, List<ElectrolizerPrognosis>> entry : currentCastMap.entrySet()) {
            Cast currentCast = entry.getKey();
            double castCobTonnage = 0;
            try {
                castCobTonnage = CastUtil.getCobTonnage(currentCast);
            } catch (Exception e) {
                LOGGER.error("Can't get tonnage fro cast: " + currentCast, e);
            }
            int electrolyzersCapacity = 0;

            List<ElectrolizerPrognosis> electrolyzerList = entry.getValue();
            electrolyzerList.removeAll(usedElectrolyzer);

            Integer lastShitft = null;
            for (ElectrolizerPrognosis electrolyzer : electrolyzerList) {
                if (castCobTonnage <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                final Cast cast = electrolyzerMap.get(electrolyzer);
                if (currentCast.equals(cast)) {
                    electrolyzersCapacity += electrolyzer.getTonnage();
                    usedElectrolyzer.add(electrolyzer);

                    lastShitft = electrolyzer.getShift();
                }
            }

            electrolyzerList.removeAll(usedElectrolyzer);

            for (ElectrolizerPrognosis electrolyzer : electrolyzerList) {
                if (castCobTonnage <= electrolyzersCapacity) {
                    break;
                }

                if (isNotLastShift(lastShitft, electrolyzer)) {
                    continue;
                }

                electrolyzersCapacity += electrolyzer.getTonnage();
                usedElectrolyzer.add(electrolyzer);

                lastShitft = electrolyzer.getShift();

            }

            if (electrolyzersCapacity >= castCobTonnage) {
                resultOrderMap.put(currentCast, usedElectrolyzer);
            }
        }


        return resultOrderMap;
    }

    private static boolean isNotLastShift(Integer lastShitft, ElectrolizerPrognosis electrolyzer) {
        return lastShitft != null && lastShitft != electrolyzer.getShift();
    }

    private static Map<Cast, List<ElectrolizerPrognosis>> getOrderMap(List<Cast> casts, List<ElectrolizerPrognosis> electrolyzers) {
        final Map<Cast, List<ElectrolizerPrognosis>> tempOrderMap = Maps.newHashMap();

        for (Cast cast : casts) {
            List<ElectrolizerPrognosis> suitableElectrolyzers = Lists.newArrayList();
            final Product product = cast.getCustomerOrder().getProduct();

            for (ElectrolizerPrognosis electrolyzer : electrolyzers) {
                if (product != null && product.isSuit(electrolyzer)) {
                    suitableElectrolyzers.add(electrolyzer);
                }
            }

            if (suitableElectrolyzers.isEmpty()) {
                continue;
            }

            tempOrderMap.put(cast, suitableElectrolyzers);
        }
        return tempOrderMap;
    }

    private static Map<ElectrolizerPrognosis, Cast> getElectolyzerMap(List<Cast> casts, List<ElectrolizerPrognosis> electrolyzers) {
        final Map<ElectrolizerPrognosis, List<Cast>> tempElectrolyzerMap = Maps.newHashMap();

        for (ElectrolizerPrognosis electrolyzer : electrolyzers) {
            List<Cast> suitableCasts = Lists.newArrayList();

            for (Cast cast : casts) {
                final CustomerOrder customerOrder = cast.getCustomerOrder();
                if (customerOrder.getProduct() != null && !electrolyzer.isSuit(customerOrder.getProduct())) {
                    suitableCasts.add(cast);
                }
            }

            if (suitableCasts.isEmpty()) {
                continue;
            }

            tempElectrolyzerMap.put(electrolyzer, suitableCasts);
        }

        final Map<ElectrolizerPrognosis, Cast> electrolyzerMap = Maps.newHashMap();

        for (Map.Entry<ElectrolizerPrognosis, List<Cast>> entry : tempElectrolyzerMap.entrySet()) {
            List<Cast> orderList = entry.getValue();
            if (orderList.size() == 1) {
                electrolyzerMap.put(entry.getKey(), orderList.iterator().next());
            }
        }

        return electrolyzerMap;
    }

    private static Map<Cast, List<ElectrolizerPrognosis>> sortByElectrolyzerCapacity(Map<Cast, List<ElectrolizerPrognosis>> unsortMap) {
        final LinkedList<Map.Entry<Cast, List<ElectrolizerPrognosis>>> entries = Lists.newLinkedList(unsortMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Cast, List<ElectrolizerPrognosis>>>() {
            @Override
            public int compare(Map.Entry<Cast, List<ElectrolizerPrognosis>> o1, Map.Entry<Cast, List<ElectrolizerPrognosis>> o2) {
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

        final Map<Cast, List<ElectrolizerPrognosis>> sortedMap = Maps.newLinkedHashMap();
        for (Map.Entry<Cast, List<ElectrolizerPrognosis>> entry : entries) {
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

    private static String listToString(List<ElectrolizerPrognosis> electrolizerPrognosises) {
        String listString = "";

        for (ElectrolizerPrognosis electrolizerPrognosis : electrolizerPrognosises) {
            listString += electrolizerPrognosis + "\t";
        }

        return listString;
    }

    private static class ElectrolizerPrognosisKey {

        private final Date date;
        private final int shift;

        private ElectrolizerPrognosisKey(Date date, int shift) {
            this.date = date;
            this.shift = shift;
        }

        public Date getDate() {
            return date;
        }

        public int getShift() {
            return shift;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ElectrolizerPrognosisKey that = (ElectrolizerPrognosisKey) o;

            if (shift != that.shift) return false;
            if (!date.equals(that.date)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = date.hashCode();
            result = 31 * result + shift;
            return result;
        }
    }
}
