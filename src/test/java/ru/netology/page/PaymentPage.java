package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.data.DateCard;
import ru.netology.utils.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.utils.JSONparts.jsonPartPayment;
import static ru.netology.utils.SQLpart.*;

public class PaymentPage {

    private SelenideElement paymentLabel = $(byText("Оплата по карте"));
    private SelenideElement numberCard = $$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement month = $$("[class='input-group__input-case']").find(exactText("Месяц"));
    private SelenideElement year = $$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement userName = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvc = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));

    public void validCardAPPROVED() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
    }

    public void validCardDECLINED() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(visible, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void cardInvalidNormalLength() {
        paymentLabel.should(visible);
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

    public void cardInvalidShortLength() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(DataHelper.getInvalidCardNumber(4));
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void cardInvalidIsNull() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue("0000000000000000");
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void cardEmpty() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("Номер карты"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void monthEmpty() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Месяц"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void yearEmpty() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Год"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void userEmpty() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
//        SelenideElement userEmpty= $(withText("Владелец"));
//        userEmpty.shouldHave(exactText("Поле обязательно для заполнения"));
        SelenideElement empty = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Владелец"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void codeEmpty() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        execButton.click();
        SelenideElement empty = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("CVC/CVV"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void cardIsSpecialSymbols() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(DataHelper.getStringIsSpecialSymbols(16));
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("Номер карты"));
        empty.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void monthIsSpecialSymbols() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(DataHelper.getStringIsSpecialSymbols(2));
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("Месяц"));
        empty.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void yearIsSpecialSymbols() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(DataHelper.getStringIsSpecialSymbols(2));
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("Год"));
        empty.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void userIsSpecialSymbols() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getStringIsSpecialSymbols(10));
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("Владелец"));
        empty.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void codeIsSpecialSymbols() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(DataHelper.getStringIsSpecialSymbols(3));
        execButton.click();
        SelenideElement empty = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white']").shouldHave(text("CVC/CVV"));
        empty.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardMonthFormatM() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard dateFormatMYY = DataHelper.getValidDateM();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(dateFormatMYY.getMonth());
        year.$("input").setValue(dateFormatMYY.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
    }

    public void validCardDateIsLast() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard dateIsLast = DataHelper.getDateIsLast();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(dateIsLast.getMonth());
        year.$("input").setValue(dateIsLast.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $("[class='input__sub']").shouldHave(exactText("Истёк срок действия карты"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardMonthInvalid() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(DataHelper.getDateInvalid().getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Месяц"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверно указан срок действия карты"));
        //$("[class='input__sub']").shouldHave(exactText("Неверно указан срок действия карты"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardYearInvalid() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(DataHelper.getDateInvalid().getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Год"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверно указан срок действия карты"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardYearInvalidFormat() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(DataHelper.getInvalidCardNumber(1));
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Год"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardUserShortName() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserShortName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
    }

    public void validCardUserLongName() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserLongName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
    }

    public void validCardUserDubleName() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserDubleName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        $(withText("Успешно")).waitUntil(visible, 15000);
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 1500);
        jsonPartPayment(new CardJSON(card.getNumber(), card.getStatus()));
        checkTransaction_id();
        checkAmount();
        checkStatusPayment(getPayment_idInBD(), card.getStatus());
    }

    public void validCardUserDifferentCaseLettersName() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserDifferentCaseLettersName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Владелец"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardUserRuName() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserRuName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Владелец"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardUserNameMore21Letters() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(DataHelper.getUserVeryLongName());
        cvc.$("input").setValue(Integer.toString(card.getCode()));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("Владелец"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }

    public void validCardCodeShort() {
        paymentLabel.should(visible);
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        numberCard.$("input").setValue(card.getNumber());
        month.$("input").setValue(card.getMonth());
        year.$("input").setValue(card.getYear());
        userName.$("input").setValue(card.getUser());
        cvc.$("input").setValue(DataHelper.getInvalidCardNumber(2));
        execButton.click();
        SelenideElement error = $("[class='input-group__input-case input-group__input-case_invalid']").shouldHave(text("CVC/CVV"));
        error.$("[class='input__sub']").shouldHave(exactText("Неверный формат"));
        $(withText("Ошибка! Банк отказал в проведении операции.")).waitUntil(hidden, 15000);
        $(withText("Успешно")).waitUntil(hidden, 15000);
    }
}
