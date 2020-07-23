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
import static ru.netology.utils.ui.QueriesToBD.getBank_id;
import static ru.netology.utils.ui.QueriesToBD.getCredit_idInBD;

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
        val formCard = credit.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        formCard.formFilling(card);
        String expectedId = getCredit_idInBD();
        String actualId = getBank_id();
        assertEquals(expectedId, actualId);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDcredit(getCredit_idInBD());
        assertEquals(expectedStatus, actualStatus);
        formCard.operationSuccess();
    }

    @Test
//issue15
    void shouldCreditCardDECLINEDValidAll() {
        val shopPage = new ShopPage();
        val credit = shopPage.credit();
        val formCard = credit.formCard();
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        formCard.formFilling(card);
        String expectedId = getCredit_idInBD();
        String actualId = getBank_id();
        assertEquals(expectedId, actualId);
        String expectedStatus = card.getStatus();
        String actualStatus = QueriesToBD.getStatusInBDcredit(getCredit_idInBD());
        assertEquals(expectedStatus, actualStatus);
        formCard.operationFall();
    }

    @Test
//issue16
    void shouldNotCreditCardInvalid() {
        val shopPage = new ShopPage();
        val credit = shopPage.credit();
        val formCard = credit.formCard();
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        card.setNumber(DataHelper.getInvalidCardNumber(16));
        formCard.formFilling(card);
        formCard.operationFall();
    }
}
