package ru.netology.utils;

import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;


import static io.restassured.RestAssured.given;
import static ru.netology.utils.AuthTest.requestSpec;

public class JSONparts {

    private JSONparts() {
    }

    public static void jsonPartCredit(CardJSON card) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(card) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/credit") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static void jsonPartPayment(CardJSON card) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(card) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/payment") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

}