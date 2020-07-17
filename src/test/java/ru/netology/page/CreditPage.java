package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.utils.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.utils.JSONparts.jsonPartCredit;

public class CreditPage {
    private SelenideElement paymentLabel = $(byText("Кредит по данным карты"));
    private SelenideElement numberCard =$$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement month =$$("[class='input-group__input-case']").find(exactText("Месяц"));
    private SelenideElement year =$$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement userName = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvc = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement pass = $("[notification__title='Успешно']");
    private SelenideElement error = $("[notification__title='Ошибка']");

    public void validCard()  {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
      jsonPartCredit(new CardJSON(card.getNumber(), card.getStatus()));
        //checkStatusCredit(id,card.getStatus());
        $(withText("Успешно")).waitUntil(visible, 15000);
    }
}
