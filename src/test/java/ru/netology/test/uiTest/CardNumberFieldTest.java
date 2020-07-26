package ru.netology.test.uiTest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.page.ShopPage;
import ru.netology.utils.ui.DataHelper;

import static com.codeborne.selenide.Selenide.open;

public class CardNumberFieldTest {
    private static String urlSUT = System.getProperty("urlSut");

    @BeforeEach
    void setUpAll() {
       open(urlSUT);
     //   open("http://localhost:8080");
    }

    @Test
        //issue4
    void waitEmptyCardError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber("");
        formCard.formFilling(card);
        formCard.operationErrorCardNumber("Поле обязательно для заполнения");
    }

    @Test
    void waitInvalidFormatErrorCardIsShortLeght() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getInvalidCardNumber(5));
        formCard.formFilling(card);
        formCard.operationErrorCardNumber("Неверный формат");
    }

    @Test
        //issue3
    void waitInvalidFormatErrorCardIsNull() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber("0000000000000000");
        formCard.formFilling(card);
        formCard.operationErrorCardNumber("Неверный формат");
    }

    @Test
    void waitInvalidFormatErrorCardIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getStringIsSpecialSymbols(16));
        formCard.formFilling(card);
        formCard.operationErrorCardNumber("Неверный формат");
    }
}
