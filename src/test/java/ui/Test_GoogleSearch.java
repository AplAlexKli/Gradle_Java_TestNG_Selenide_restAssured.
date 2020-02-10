package ui;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by admin on 10.02.2020.
 *
 *
 * UI тестирование
 *
 * запустить Chrome
 * открыть https://www.google.com/
 * написать в строке поиска «Открытие»
 * нажать Поиск
 * проверить, что результатах поиска есть https://www.open.ru
 * перейти на сайт https://www.open.ru
 * проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.
 */
public class Test_GoogleSearch {

    @Test
    void searchOpenInGoogle() {

        open("https://www.google.com/");

        $(byName("q")).setValue("Открытие").pressEscape();

        $("div:not([jsname]) > center [value='Поиск в Google']").click();

        ElementsCollection searchResults = $$("#search .g > :not(table) > div .r > a");
        searchResults.filterBy(attribute("href", "https://www.open.ru/"))
                .shouldHave(sizeGreaterThanOrEqual(1))
                .first().shouldBe(visible).click();

        //елементы, представляющие отдельные строки таблицы по каждой валюте. далее из них вытягиваются курсы валют
        SelenideElement usdRow = $x("//span[text()='USD']").closest("tr"),
               eurRow = $x("//span[text()='EUR']").closest("tr");

        // преобразование курсов для сравнения
        String usdBuy = usdRow.innerText().split("/")[0].replace("USD","").replace(",","."),
                usdSell = usdRow.innerText().split("/")[1].replace(",","."),
                eurBuy = eurRow.innerText().split("/")[0].replace("EUR","").replace(",","."),
                eurSell = eurRow.innerText().split("/")[1].replace(",",".");

        Assert.assertTrue(new Double(usdSell) > new Double(usdBuy));
        Assert.assertTrue(new Double(eurSell) > new Double(eurBuy));
    }

}
