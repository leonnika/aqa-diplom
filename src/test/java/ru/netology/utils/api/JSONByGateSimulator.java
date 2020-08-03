package ru.netology.utils.api;

import io.restassured.response.Response;
import ru.netology.data.CardJSON;

import static io.restassured.RestAssured.given;
import static ru.netology.utils.api.AuthTest.requestSpec;

public class JSONByGateSimulator {

    private JSONByGateSimulator() {
    }

    public static String jsonByCredit(CardJSON card) {
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
        return status;
    }

    public static String jsonByPayment(CardJSON card) {
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
        return status;
    }

    public static void jsonByPaymentInvalidCard(CardJSON card) {
        given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post("/payment")
                .then()
                .statusCode(400);
    }

    public static void jsonByCreditInvalidCard(CardJSON card) {
        given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post("/credit")
                .then()
                .statusCode(400);
    }
}