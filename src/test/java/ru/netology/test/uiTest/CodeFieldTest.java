package ru.netology.test.uiTest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.page.ShopPage;
import ru.netology.utils.ui.DataHelper;

import static com.codeborne.selenide.Selenide.open;

public class CodeFieldTest {
    private static String urlSUT = System.getProperty("urlSut");

    @BeforeEach
    void setUpAll() {
       open(urlSUT);
    //    open("http://localhost:8080");
    }

    @Test
        //issue7
    void waitEmptyCodeError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        formCard.codeEmpty();
        formCard.operationErrorCodeNumber("Поле обязательно для заполнения");
    }

    @Test
    void waitInvalidFormatErrorCodeShort() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setCode(Integer.parseInt(DataHelper.getInvalidCardNumber(2)));
        formCard.formFilling(card);
        formCard.operationErrorField("CVC/CVV", "Неверный формат");
    }

    @Test
    void waitInvalidFormatErrorCodeIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        formCard.codeIsSpecialSymbols();
        formCard.operationErrorCodeNumber( "Неверный формат");
    }
}
