package ru.netology.test.uiTest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.page.ShopPage;
import ru.netology.utils.ui.DataHelper;

import static com.codeborne.selenide.Selenide.open;

public class CardNumberFieldTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:8080");
    }


    @Test
        //issue2
    void shouldNotPaymentCardInvalidNormalLeght() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getInvalidCardNumber(16));
        formCard.formFilling(card);
        formCard.OperationFall();
    }

    @Test
        //issue4
    void shouldNotPaymentCardEmpty() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber("");
        formCard.formFilling(card);
        formCard.OperationErrorCardNumber("Поле обязательно для заполнения");
    }

    @Test
    void shouldNotPaymentCardInvalidShortLeght() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getInvalidCardNumber(5));
        formCard.formFilling(card);
        formCard.OperationErrorCardNumber("Неверный формат");
    }

    @Test
        //issue3
    void shouldNotPaymentCardInvalidIsNull() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber("0000000000000000");
        formCard.formFilling(card);
        formCard.OperationErrorCardNumber("Неверный формат");
    }

    @Test
    void shouldNotPaymentCardIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getStringIsSpecialSymbols(16));
        formCard.formFilling(card);
        formCard.OperationErrorCardNumber("Неверный формат");
    }
}
