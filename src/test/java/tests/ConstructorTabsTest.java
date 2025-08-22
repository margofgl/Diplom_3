package tests;

import config.TestBase;
import io.qameta.allure.Description;
import org.junit.Test;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTabsTest extends TestBase {

    @Test
    @Description("Вкладки конструктора переключаются: Булки / Соусы / Начинки")
    public void tabsSwitching() {
        MainPage main = new MainPage(driver);
        main.open();

        main.openSauces();
        assertTrue("Вкладка 'Соусы' не активна", main.isActiveTab("Соусы"));

        main.openFillings();
        assertTrue("Вкладка 'Начинки' не активна", main.isActiveTab("Начинки"));

        main.openBuns();
        assertTrue("Вкладка 'Булки' не активна", main.isActiveTab("Булки"));
    }
}