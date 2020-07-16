package ru.netology.utils;

import io.restassured.response.Response;
import ru.netology.data.CardJSON;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.netology.utils.AuthTest.requestSpec;

public class JSONparts {

    private JSONparts() {
    }

    public static void jsonPartCredit(CardJSON card) {
        Response response =
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .body(card) // передаём в теле объект, который будет преобразован в JSON
                        .when() // "когда"
                        .post("/credit") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200) // код 200 OK
                        .extract()
                        .response();


        String status = response.path("status");

        // используются matcher'ы Hamcrest
        assertThat(status, equalTo(card.getStatus()));

    }

    public static void jsonPartPayment(CardJSON card) {
        Response response =
                given() // "дано"
                        .spec(requestSpec) // указываем, какую спецификацию используем
                        .body(card) // передаём в теле объект, который будет преобразован в JSON
                        .when() // "когда"
                        .post("/payment") // на какой путь, относительно BaseUri отправляем запрос
                        .then() // "тогда ожидаем"
                        .statusCode(200) // код 200 OK
                        .extract()
                        .response();

        String status = response.path("status");

        // используются matcher'ы Hamcrest
        assertThat(status, equalTo(card.getStatus()));

    }

}