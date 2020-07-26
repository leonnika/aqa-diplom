package ru.netology.test.uiTest;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.DateCard;
import ru.netology.page.ShopPage;
import ru.netology.utils.ui.DataHelper;
import ru.netology.utils.ui.QueriesToBD;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.utils.ui.QueriesToBD.getPayment_idInBD;
import static ru.netology.utils.ui.QueriesToBD.getTransaction_id;

public class DateFieldTest {
    private static String urlSUT = System.getProperty("urlSut");

    @BeforeEach
    void setUpAll() {
       open(urlSUT);
     //   open("http://localhost:8080");
    }

    @Test
        //issue5
    void waitEmptyMonthError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setMonth("");
        formCard.formFilling(card);
        formCard.operationErrorField("Месяц", "Поле обязательно для заполнения");
    }

    @Test
        //issue6
    void waitEmptyYearError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setYear("");
        formCard.formFilling(card);
        formCard.operationErrorField("Год", "Поле обязательно для заполнения");
    }

    @Test
//issue9
    void assertSuccessPaymentValidAllDateFormatMYY() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard date = DataHelper.getValidDateM();
        card.setMonth(date.getMonth());
        card.setYear(date.getYear());
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
    void waitDateIsLastError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard date = DataHelper.getDateIsLast();
        card.setMonth(date.getMonth());
        card.setYear(date.getYear());
        formCard.formFilling(card);
        formCard.operationErrorField("Год", "Истёк срок действия карты");
    }

    @Test
    void waitInvalidMonthError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard date = DataHelper.getDateInvalid();
        card.setMonth(date.getMonth());
        formCard.formFilling(card);
        formCard.operationErrorField("Месяц", "Неверно указан срок действия карты");
    }

    @Test
    void waitInvalidYearError() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        DateCard date = DataHelper.getDateInvalid();
        card.setYear(date.getYear());
        formCard.formFilling(card);
        formCard.operationErrorField("Год", "Неверно указан срок действия карты");
    }

    @Test
    void waitInvalidYearErrorFormat() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setYear(DataHelper.getInvalidCardNumber(1));
        formCard.formFilling(card);
        formCard.operationErrorField("Год", "Неверный формат");
    }

    @Test
    void waitInvalidFormatErrorMonthIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setMonth(DataHelper.getStringIsSpecialSymbols(2));
        formCard.formFilling(card);
        formCard.operationErrorField("Месяц", "Неверный формат");
    }

    @Test
    void waitInvalidFormatErrorYearIsSpecialSymbols() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setYear(DataHelper.getStringIsSpecialSymbols(2));
        formCard.formFilling(card);
        formCard.operationErrorField("Год", "Неверный формат");
    }
}
