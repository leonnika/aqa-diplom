package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.ShopPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
    @BeforeEach
    void setUpAll() {
        open("http://localhost:8080");
    }

    @Test
    void shouldPaymentValidAll() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        payment.validCard();
         //     System.out.println(DataHelper.getСurrentAmount());
            }
}

