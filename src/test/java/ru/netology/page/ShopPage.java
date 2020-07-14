package ru.netology.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ShopPage {

    public CreditPage credit(){
        $$("button").find(exactText("Купить в кредит")).click();
        return new CreditPage();
    }

    public PaymentPage payment(){
        $$("button").find(exactText("Купить")).click();
        return new PaymentPage();
    }
}
