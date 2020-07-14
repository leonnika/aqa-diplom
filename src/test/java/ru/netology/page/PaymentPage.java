package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement paymentLabel = $(byText("Оплата по карте"));
    private SelenideElement numberCard = $("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_has-value input_invalid input_theme_alfa-on-white']");
    private SelenideElement month = $("[input__top=Месяц]");
    private SelenideElement year = $("[input__top=Год]");
    private SelenideElement userName = $("[input__top=Владелец]");
    private SelenideElement cvc = $("[input__top=CVC/CVV]");
   private SelenideElement execButton = $$("button").find(exactText("Продолжить"));
   private SelenideElement pass = $("[notification__title='Успешно']");
    private SelenideElement error = $("[notification__title='Ошибка']");

    public void validCard() {
     //  paymentLabel.should(visible);
       numberCard.$("input").setValue(DataHelper.getValidCardInfoAPPROVED().getNumber());
       month.$("input").setValue(DataHelper.getValidCardInfoAPPROVED().getMonth());
       year.$("[class='input__control']").setValue(DataHelper.getValidCardInfoAPPROVED().getYear());
       userName.$("[class='input__control']").setValue(DataHelper.getValidCardInfoAPPROVED().getUser());
       cvc.$("[class='input__control']").setValue(Integer.toString(DataHelper.getValidCardInfoAPPROVED().getCode()));
        execButton.click();
        $(withText("Операция одобрена Банком.")).waitUntil(visible, 150);
    }

}
