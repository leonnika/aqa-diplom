package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.ShopPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:8080");
    }

    @Test
        //issue14
    void shouldCreditCardAPPROVEDValidAll() {
        val shopPage = new ShopPage();
        val credit = shopPage.credit();
        credit.validCardAPPROVED();
    }

    @Test
//issue15
    void shouldCreditCardDECLINEDValidAll() {
        val shopPage = new ShopPage();
        val credit = shopPage.credit();
        credit.validCardDECLINED();
    }

    @Test
//issue16
    void shouldNotCreditCardInvalid() {
        val shopPage = new ShopPage();
        val credit = shopPage.credit();
        credit.cardInvalidNormalLength();
    }
    // аналогичные тесты для всех полей как для payment. Нужно писать?
}
