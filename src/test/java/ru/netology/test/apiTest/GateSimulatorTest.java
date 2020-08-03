package ru.netology.test.apiTest;

import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.utils.ui.DataHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.netology.utils.api.JSONByGateSimulator.*;

public class GateSimulatorTest {

    @Test
    void checkStatusByPaymentGateAndStatusAPPROVED() {
        CardJSON card = DataHelper.getValidCardJsonAPPROVED();
        String actual = jsonByPayment(card);
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void checkStatusByPaymentGateAndStatusDECLINED() {
        CardJSON card = DataHelper.getValidCardJsonDECLINED();
        String actual = jsonByPayment(card);
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void checkStatusByCreditGateAndStatusAPPROVED() {
        CardJSON card = DataHelper.getValidCardJsonAPPROVED();
        String actual = jsonByCredit(card);
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void checkStatusByCreditGateAndStatusDECLINED() {
        CardJSON card = DataHelper.getValidCardJsonDECLINED();
        String actual = jsonByCredit(card);
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void waitStatusCode400ByCreditGateInvalidCard() {
        CardJSON card = DataHelper.getInvalidCardJsonDECLINED();
        jsonByCreditInvalidCard(card);
    }

    @Test
    void waitStatusCode400ByPaymentGateInvalidCard() {
        CardJSON card = DataHelper.getInvalidCardJsonDECLINED();
        jsonByPaymentInvalidCard(card);
    }
}
