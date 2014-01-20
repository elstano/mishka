package ru.org.icad.mishka.web.gwt.main.client.menu;

import com.google.common.collect.Lists;
import ru.org.icad.mishka.web.gwt.main.client.menu.bug.BugPresenter;
import ru.org.icad.mishka.web.gwt.main.client.menu.theme.MenuRSBundle;
import ru.org.icad.mishka.web.gwt.main.client.settings.SettingsPresenter;
import ru.org.icad.mishka.web.gwt.main.client.upload.UploadPresenter;

import java.util.List;

/**
 * User: Boss
 * Date: 10/6/13
 * Time: 10:29 PM
 */
public class MenuDataManager {
    private List<MenuItem> items = Lists.newArrayList();

    {
        items.add(new MenuItem(MenuRSBundle.INSTANCE.factories(), "Заводы", new UploadPresenter("о заводах")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.products(), "Продукты", new UploadPresenter("о продуктах")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.nsi(), "НСИ", new UploadPresenter("об НСИ")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.schedule(), "Расписание ЛА", new UploadPresenter("о расписании ЛА")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.filter(), "Фильтр", new UploadPresenter("о фильтрах")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.sgp(), "СГП/НЗП", new UploadPresenter("об СГП/НЗП")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.chemistry(), "Химия Сырца", new UploadPresenter("о химии сырца")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.transport(), "Транспорт", new UploadPresenter("о транспорте")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.orders(), "Пакет Заказов", new UploadPresenter("о пакете заказов")));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.settings(), "Расчетный Модуль", new SettingsPresenter()));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.resultSchedule(), "Расписание", null));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.bug(), "Отладка", new BugPresenter()));
    }

    public List<MenuItem> getMenuItems() {
        return items;
    }
}
