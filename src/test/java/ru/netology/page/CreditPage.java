package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.utils.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.utils.JSONparts.jsonPartCredit;
import static ru.netology.utils.JSONparts.jsonPartPayment;
import static ru.netology.utils.SQLpart.*;
import static ru.netology.utils.SQLpart.getPayment_idInBD;

public class CreditPage {
    private SelenideElement creditLabel = $(byText("Кредит по данным карты"));
    private SelenideElement numberCard =$$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement month =$$("[class='input-group__input-case']").find(exactText("Месяц"));
    private SelenideElement year =$$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement userName = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvc = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));

    public void validCardAPPROVED()  {
        creditLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartCredit(new CardJSON(card.getNumber(), card.getStatus()));
        checkBank_id();
       checkStatusCredit(getCredit_idInBD(),card.getStatus());
        }

    public void validCardDECLINED() {
        creditLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        jsonPartCredit(new CardJSON(card.getNumber(), card.getStatus()));
        checkBank_id();
        checkStatusCredit(getCredit_idInBD(), card.getStatus());
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(visible, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void cardInvalidNormalLength() {
        creditLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(DataHelper.getInvalidCardNumber(16));
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(visible, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
        //? нужно ли json запрос здесь
    }
}
