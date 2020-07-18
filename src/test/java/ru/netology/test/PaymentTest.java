package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.page.ShopPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {


    @BeforeEach
    void setUpAll() {
        open("http://localhost:8080");
    }

    @Test
    void shouldPaymentCardAPPROVEDValidAll() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        payment.validCardAPPROVED();
    }

    @Test
        //issue1
    void shouldNotPaymentCardDECLINEDValidAll() {
        val shopPage = new ShopPage();
        val payment = shopPage.payment();
        payment.validCardDECLINED();
    }

    @Nested
    class CardOtions {

        @Test
            //issue2
        void shouldNotPaymentCardInvalidNormalLeght() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.cardInvalidNormalLength();
        }

        @Test
        void shouldNotPaymentCardInvalidShortLeght() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.cardInvalidShortLength();
        }

        @Test
            //issue3
        void shouldNotPaymentCardInvalidIsNull() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.cardInvalidIsNull();
        }
    }

    @Nested
    class EmptyFieldsOtions {

        @Test
            //issue4
        void shouldNotPaymentCardEmpty() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.cardEmpty();
        }

        @Test
            //issue5
        void shouldNotPaymentMonthEmpty() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.monthEmpty();
        }

        @Test
            //issue6
        void shouldNotPaymentYearEmpty() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.yearEmpty();
        }

        @Test
        void shouldNotPaymentUserEmpty() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.userEmpty();
        }

        @Test
            //issue7
        void shouldNotPaymentCodeEmpty() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.codeEmpty();
        }
    }

    @Nested
    class SpecialSymbolsInFieldsOtions {

        @Test
        void shouldNotPaymentCardIsSpecialSymbols() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.cardIsSpecialSymbols();
        }

        @Test
        void shouldNotPaymentMonthIsSpecialSymbols() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.monthIsSpecialSymbols();
        }

        @Test
        void shouldNotPaymentYearIsSpecialSymbols() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.yearIsSpecialSymbols();
        }

        @Test
            //issue8
        void shouldNotPaymentUserIsSpecialSymbols() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.userIsSpecialSymbols();
        }

        @Test
        void shouldNotPaymentCodeIsSpecialSymbols() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.codeIsSpecialSymbols();
        }
    }

    @Nested
    class DateOtions {

        @Test
//issue9
        void shouldNotPaymentValidAllDateFormatMYY() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardMonthFormatM();
        }

        @Test
        void shouldNotPaymentValidAllDateIsLast() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardDateIsLast();
        }

        @Test
        void shouldNotPaymentValidAllMonthInvalid() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardMonthInvalid();
        }

        @Test
        void shouldNotPaymentValidAllYearInvalid() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardYearInvalid();
        }

        @Test
        void shouldNotPaymentValidAllYearInvalidFormat() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardYearInvalidFormat();
        }

    }

    @Nested
    class UserOtions {

        @Test
        void shouldPaymentValidAllUserShortName() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserShortName();
        }

        @Test
        void shouldPaymentValidAllUserLongName() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserLongName();
        }

        @Test
            //issue10
        void shouldNotPaymentValidAllUserDifferentCaseLettersName() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserDifferentCaseLettersName();
        }

        @Test
        void shouldPaymentValidAllUserDubleName() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserDubleName();
        }

        @Test
//issue11
        void shouldNotPaymentValidAllUserRuName() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserRuName();
        }

        @Test
//issue12
        void shouldNotPaymentValidAllUserMore21Letters() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardUserNameMore21Letters();
        }

    }

    @Nested
    class CodeOtions {

        @Test
        void shouldPaymentValidAllCodeShort() {
            val shopPage = new ShopPage();
            val payment = shopPage.payment();
            payment.validCardCodeShort();
        }
    }
}

