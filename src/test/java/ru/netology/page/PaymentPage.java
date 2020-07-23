package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement paymentLabel = $(byText("Оплата по карте"));

    public FormCardPage formCard() {
        paymentLabel.should(visible);

        return new FormCardPage();
    }
}
