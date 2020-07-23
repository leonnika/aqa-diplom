package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import ru.netology.utils.ui.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormCardPage {
    private SelenideElement numberCard = $$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement month = $$("[class='input-group__input-case']").find(exactText("Месяц"));
    private SelenideElement year = $$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement userName = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvc = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));

    public void formFilling(CardInfo card) {
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
    }

    public void operationSuccess() {
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
    }

    public void operationFall() {
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(visible, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void operationErrorCardNumber(String errorMessage) {
        SelenideElement error = $("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").shouldHave(text("Номер карты"));
        error.$("[class='input__sub']").shouldHave(exactText(errorMessage));
    }

    public void operationErrorField(String field, String errorMessage) {
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text(field));
        error.$("[class='input__sub']").shouldHave(exactText(errorMessage));
    }

    public void operationErrorCodeNumber(String errorMessage) {
        SelenideElement error = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("CVC/CVV"));
        error.$("[class='input__sub']").shouldHave(exactText(errorMessage));
    }

    public void codeEmpty() {
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        execButton.click();
    }

    public void codeIsSpecialSymbols() {
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(DataHelper.getStringIsSpecialSymbols(3));
        execButton.click();
    }
}

