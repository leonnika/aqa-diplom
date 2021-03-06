package ru.netology.test.uiTest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.page.ShopPage;
import ru.netology.utils.ui.DataHelper;
import ru.netology.utils.ui.QueriesToBD;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.utils.ui.QueriesToBD.getPayment_idInBD;
import static ru.netology.utils.ui.QueriesToBD.getTransaction_id;

public class UserFieldTest {
    private static String urlSUT = System.getProperty("urlSut");

    @BeforeEach
    void setUpAll() {
       open(urlSUT);
      //  open("http://localhost:8080");
    }

    @Test
    void waitEmptyUserError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser("");
        formCard.formFilling(card);
        formCard.operationErrorField("Владелец", "Поле обязательно для заполнения");

    }

    @Test
    void assertSuccessPaymentValidAllUserShortName() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserShortName());
        formCard.formFilling(card);
        formCard.operationSuccess();
        String expectedId = getPayment_idInBD();
        String actualId = getTransaction_id();
        assertEquals(expectedId, actualId);
        int expectedAmount = Integer.parseInt(DataHelper.getСurrentAmount());
        int actualAmount = QueriesToBD.getAmountInBDpayment(getPayment_idInBD());
        assertEquals(expectedAmount, actualAmount);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDpayment(getPayment_idInBD());
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void assertSuccessPaymentValidAllUserLongName() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserLongName());
        formCard.formFilling(card);
        formCard.operationSuccess();
        String expectedId = getPayment_idInBD();
        String actualId = getTransaction_id();
        assertEquals(expectedId, actualId);
        int expectedAmount = Integer.parseInt(DataHelper.getСurrentAmount());
        int actualAmount = QueriesToBD.getAmountInBDpayment(getPayment_idInBD());
        assertEquals(expectedAmount, actualAmount);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDpayment(getPayment_idInBD());
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
        //issue8
    void waitInvalidFormatErrorUserIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getStringIsSpecialSymbols(7));
        formCard.formFilling(card);
        formCard.operationErrorField("Владелец", "Поле обязательно для заполнения");
    }

    @Test
        //issue10
    void waitInvalidFormatErrorUserDifferentCaseLettersName() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserDifferentCaseLettersName());
        formCard.formFilling(card);
        formCard.operationErrorField("Владелец", "Неверный формат");
    }

    @Test
    void assertSuccessPaymentValidAllUserDubleName() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserDubleName());
        formCard.formFilling(card);
        formCard.operationSuccess();
        String expectedId = getPayment_idInBD();
        String actualId = getTransaction_id();
        assertEquals(expectedId, actualId);
        int expectedAmount = Integer.parseInt(DataHelper.getСurrentAmount());
        int actualAmount = QueriesToBD.getAmountInBDpayment(getPayment_idInBD());
        assertEquals(expectedAmount, actualAmount);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDpayment(getPayment_idInBD());
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
//issue11
    void waitInvalidFormatErrorUserRuName() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserRuName());
        formCard.formFilling(card);
        formCard.operationErrorField("Владелец", "Неверный формат");
    }

    @Test
//issue12
    void waitInvalidFormatErrorUserMore21Letters() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setUser(DataHelper.getUserVeryLongName());
        formCard.formFilling(card);
        formCard.operationErrorField("Владелец", "Неверный формат");
    }
}
