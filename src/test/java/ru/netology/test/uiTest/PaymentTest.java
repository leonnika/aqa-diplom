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

public class PaymentTest {

    private static String urlSUT = System.getProperty("urlSut");

    @BeforeEach
    void setUpAll() {
        open(urlSUT);
    }

    @Test
    void shouldPaymentCardAPPROVEDValidAll() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        formCard.formFilling(card);
        String expectedId = getPayment_idInBD();
        String actualId = getTransaction_id();
        assertEquals(expectedId, actualId);
        int expectedAmount = Integer.parseInt(DataHelper.getСurrentAmount());
        int actualAmount = QueriesToBD.getAmountInBDpayment(getTransaction_id());
        assertEquals(expectedAmount, actualAmount);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDpayment(getTransaction_id());
        assertEquals(expectedStatus, actualStatus);
        formCard.operationSuccess();
    }

    @Test
        //issue1
    void shouldNotPaymentCardDECLINEDValidAll() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        val formCard = payment.formCard();
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        formCard.formFilling(card);
        formCard.operationFall();
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
}

