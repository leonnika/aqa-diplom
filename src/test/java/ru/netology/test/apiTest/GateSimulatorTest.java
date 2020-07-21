package ru.netology.test.apiTest;

import org.junit.jupiter.api.Test;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.utils.ui.DataHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.netology.utils.api.JSONByGateSimulator.jsonByCredit;
import static ru.netology.utils.api.JSONByGateSimulator.jsonByPayment;

public class GateSimulatorTest {

    @Test
    void shouldGetStatusByPaymentGateStatusAPPROVED() {
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        String actual = jsonByPayment(new CardJSON(card.getNumber(), card.getStatus()));
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void shouldGetStatusByPaymentGateStatusDECLINED() {
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        String actual = jsonByPayment(new CardJSON(card.getNumber(), card.getStatus()));
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void shouldGetStatusByCreditGateStatusAPPROVED() {
        CardInfo card = DataHelper.getValidCardInfoAPPROVED();
        String actual = jsonByCredit(new CardJSON(card.getNumber(), card.getStatus()));
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void shouldGetStatusByCreditGateStatusDECLINED() {
        CardInfo card = DataHelper.getValidCardInfoDECLINED();
        String actual = jsonByCredit(new CardJSON(card.getNumber(), card.getStatus()));
        String expected = card.getStatus();
        assertThat(actual, equalTo(expected));
    }
}
