# Отчёт о проведённом тестировании комплексного веб-сервиса, взаимодействующего с СУБД и API Банка.

[![Build status](https://ci.appveyor.com/api/projects/status/54jnx0wsn98v59x8/branch/master?svg=true)](https://ci.appveyor.com/project/leonnika/aqa-diplom/branch/master)

## Краткое описание

В ранках выполнения дипломного проекта было автоматизированно комплексное тестирование веб-сервиса и симулятора банковского сервиса. При разработке использовались патерн Page Objects, Faker, Lombok, утилитный класс-генератор данных. Были написаны тесты с  запросами в базу, проверяющие корректность внесения приложением информации, при помощи SQL. В систему автоматизации  интегрированы отчёты Gradle. 

### Тестовые сценарии
 Было проведено:
* позитивное и негативное UI тестирование  
* функциональное тестирование покупки тура по карте
* функциональное тестирование покупки тура по карте в кредит
* функциональное тестирование симулятора банковского сервиса  Payment Gate и Credit Gate через API 

### Используемые инструменты 
1. Windows11 X64
2. Браузер Google Chrome v.83.0.4103.97
3. OpenJDK 11 (LTS)
4. IntelliJ IDEA 2019.2.4 (Community Edition)
5. Junit-jupiter v.5.6.1
6. Selenide v.5.12.0
7. Javafaker v.1.0.1
8. Проект на базе сборки Gradle
9. Docker v.2.3.0.3(45519)
10. Rest-assured v.4.3.0
11. Gson v.2.8.6
12. Веб-сервис aqa-shop.jar c симулятором банковских сервисов

### Тестовые данные
Для тестирования симулятора с помощью автотестов API был использованн набор карт в формате JSON :
```
{
    "number": "4444 4444 4444 4441",
    "status": "APPROVED"
  },
  {
    "number": "4444 4444 4444 4442",
    "status": "DECLINED"
  }

  {
    "number": "4444 4444 4444 4444",
    "status": "DECLINED"
  }
```
Для UI тестирования в качестве валидных номеров карт использовался тот же набор карт как и для API тестирования.Все тестовые данные генерировались случайным образом с помощью методов класса DataHelper

## Количество тест-кейсов
В проекте реализовано 36 тест-кейсов.


<details>
  <summary>Все тест-кейсы подробно</summary>

№тест-кейса | метод  |тестовые данные| описание
--- | --- | ---|----
*API тестирование*|Проверка верного возврата статуса от симулятора
1|checkStatusByPaymentGateAndStatusAPPROVED|карта 4444 4444 4444 4441|тесирование Payment Gate. 
2|checkStatusByPaymentGateAndStatusDECLINED|карта 4444 4444 4444 4442|тесирование Payment Gate
3|checkStatusByCreditGateAndStatusAPPROVED|карта 4444 4444 4444 4441|тесирование Credit Gate
4|checkStatusByCreditGateAndStatusDECLINED|карта 4444 4444 4444 4442|тесирование Credit Gate
5|waitStatusCode400ByCreditGateInvalidCard|карта 4444 4444 4444 4444|тесирование Credit Gate невадный номер карты
6|waitStatusCode400ByPaymentGateInvalidCard|карта 4444 4444 4444 4444|тесирование Payment Gate невалидный номер карты
*IU тестирование*| функциональное тестирование
7|assertSuccessPaymentCardAPPROVEDValidAll|карта 4444 4444 4444 4441, все данные в форме валидные|функциональное тестирование покупки тура по карте.
8|assertSuccessCreditCardAPPROVEDValidAll|карта 4444 4444 4444 4441, все данные в форме валидные|функциональное тестирование покупки тура по карте в кредит. 
9|waitFailurePaymentCardDECLINEDValidAll|карта 4444 4444 4444 4442, все данные в форме валидные|функциональное тестирование покупки тура по карте со статусом DECLINED. 
10|waitFailureCreditCardDECLINEDValidAll|карта 4444 4444 4444 4442, все данные в форме валидные|функциональное тестирование покупки тура по карте в кредит со статусом DECLINED. 
*IU тестирование*| функциональное негативное тестирование
11|waitErrorPaymentCardIsNormalLeght|сгенерированный невалидный номер карты, все данные в форме валидные|функциональное тестирование покупки тура по карте. 
12|waitErrorCreditCardInvalid|сгенерированный невалидный номер карты, все данные в форме валидные|функциональное тестирование покупки тура по карте в кредит. 
*IU тестирование*| тестирование обработки ввода невалидных данных в поля формы
13|waitEmptyCardError|пустое поле номер карты, все данные в форме валидные|тестирование обработки пустого поля. 
14|waitInvalidFormatErrorCardIsShortLeght|номер карты менее 16 символов, все данные в форме валидные|тестирование обработки неверного формата номера карты.
15|waitInvalidFormatErrorCardIsNull|номер карты состоит из 0, все данные в форме валидные|тестирование обработки неверного формата номера карты.
16|waitInvalidFormatErrorCardIsSpecialSymbols|номер карты состоит из специальных символов, все данные в форме валидные|тестирование обработки неверного формата номера карты.
17|waitEmptyCodeError|пустое поле "CVC/CVV", все данные в форме валидные|тестирование обработки пустого поля.
18|waitInvalidFormatErrorCodeShort|код менее 3х цифр, все данные в форме валидные|тестирование обработки неверного формата поля "CVC/CVV".
19|waitInvalidFormatErrorCodeIsSpecialSymbols|код состоит из специальных символов, все данные в форме валидные|тестирование обработки неверного формата поля "CVC/CVV".
20|waitEmptyMonthError|пустое поле месяц, все данные в форме валидные|тестирование обработки пустого поля.
21|waitEmptyYearError|пустое поле год, все данные в форме валидные|тестирование обработки пустого поля.
22|assertSuccessPaymentValidAllDateFormatMYY|введен месяц в формате одной цифры, все данные в форме валидные|функциональное тестирование покупки тура и обработка такого формата месяца.
23|waitDateIsLastError|сгенерированна дата, при которой срок действия карты будет истекшим, все данные в форме валидные|тестирование обработки даты, при которой, истёкает срок действия карты .
24|waitInvalidMonthError|месяц невалидный, все данные в форме валидные|тестирование обработки неверного формата поля месяц.
25|waitInvalidYearError|год невалидный, все данные в форме валидные|тестирование обработки неверного формата поля год.
26|waitInvalidYearErrorFormat|сгод введен одной цифрой, все данные в форме валидные|тестирование обработки неверного формата поля год.
27|waitInvalidFormatErrorMonthIsSpecialSymbols|месяц из специальных символов , все данные в форме валидные|тестирование обработки неверного формата поля месяц.
28|waitInvalidFormatErrorYearIsSpecialSymbols|год из специальных символов, все данные в форме валидные|тестирование обработки неверного формата поля год.
29|waitEmptyUserError|пустое поле владелец, все данные в форме валидные|тестирование обработки пустого поля.
30|assertSuccessPaymentValidAllUserShortName|поле владелец состоит из короткого имени, все данные в форме валидные|функциональное тестирование покупки тура по карте.
31|assertSuccessPaymentValidAllUserLongName|поле владелец состоит из длинного имени, все данные в форме валидные|функциональное тестирование покупки тура по карте.
32|waitInvalidFormatErrorUserIsSpecialSymbols|владелец состоит из специальных символов, все данные в форме валидные|тестирование обработки неверного формата поля.
33|waitInvalidFormatErrorUserDifferentCaseLettersName|владелец состоит из символов разного регистра, все данные в форме валидные|тестирование обработки неверного формата поля.
34|assertSuccessPaymentValidAllUserDubleName|владелец состоит из двойного имени, все данные в форме валидные|функциональное тестирование покупки тура по карте.
35|waitInvalidFormatErrorUserRuName|владелец состоит из символов кириллицы, все данные в форме валидные|тестирование обработки неверного формата поля.
36|waitInvalidFormatErrorUserMore21Letters|владелец состоит из более 21 символов, все данные в форме валидные|тестирование обработки неверного формата поля.
</details>

## Соотношение успешных и не успешных тест-кейсов

Всего тест-кейсов | Успешных   | Не успешных| Процент успешного выполнения
--- | --- | ---|----
36|21|15|58%

<details>
  <summary>Подробнее отчет на базе Gradle</summary>

![Отчет](https://github.com/leonnika/aqa-diplom/blob/master/docs/png/report/gradle_report.PNG) 

</details>

## Общие рекомендации
В ходе проведения тестирования было выявлено 15 ошибок веб-сервиса(на каждую заведен баг-репорт). Из них выявлены три критические ошибки функциональной работы приложения:
1. При покупки в кредит система неверно создает запись в базе данных.

[Сервис не записывает credit_id в таблицу order_entity при покупки в кредит по карте со статусом APPROVED](https://github.com/leonnika/aqa-diplom/issues/16)


2. При совершении операции по карте и по карте в кредит со статусом DECLINED, пользователю приходит сообщение об успешном совершении операции.

[Ошибочный вывод сообщения "Успешно" при покупке по карте при статусе транзакции DECLINED](https://github.com/leonnika/aqa-diplom/issues/4)

[Ошибочный вывод сообщения "Успешно" при покупке в кредит по карте при статусе транзакции DECLINED](https://github.com/leonnika/aqa-diplom/issues/17)

3. При совершении операции по карте и по карте в кредит с невалидным номером, пользователю приходит сообщение об отказе в операции, а потом сообщение об успешной операции.

[Ошибочный вывод сообщения "Успешно!" при покупке по невалидному номеру карты](https://github.com/leonnika/aqa-diplom/issues/5)

[Ошибочный вывод сообщения "Успешно!" при покупке в кредит по невалидному номеру карты ](https://github.com/leonnika/aqa-diplom/issues/18)

Остальные ошибки приложения:

4. [Сервис обрабатывает запрос при вводе 0000 0000 0000 0000 в поле "номер карты".](https://github.com/leonnika/aqa-diplom/issues/6)

5. [Ошибочное сообщение о неверном формате при пустом поле "Номер карты"](https://github.com/leonnika/aqa-diplom/issues/7)

6. [Ошибочное сообщение о неверном формате при пустом поле "Меся](https://github.com/leonnika/aqa-diplom/issues/8)
7. [Ошибочное сообщение о неверном формате при пустом поле "Год"](https://github.com/leonnika/aqa-diplom/issues/9)
8. [Ошибочное сообщение о неверном формате при пустом поле "CVC/CVV"](https://github.com/leonnika/aqa-diplom/issues/10)
9. [Не проходит операция покупки при вводе в поле "месяц" номера месяца одной цифрой](https://github.com/leonnika/aqa-diplom/issues/11)
10. [Ошибочная обработка заявки при вводе специальных символов в поле "Владелец"](https://github.com/leonnika/aqa-diplom/issues/12)
11. [Ошибочная обработка заявки при вводе значения в разных регистрах в поле "Владелец"](https://github.com/leonnika/aqa-diplom/issues/13)
12. [Ошибочная обработка заявки при вводе значения кириллицей в поле "Владелец"](https://github.com/leonnika/aqa-diplom/issues/14)
13. [Ошибочная обработка заявки при вводе значения, которое более 21 символа в поле "Владелец"](https://github.com/leonnika/aqa-diplom/issues/15)

Сервис нуждается в доработке. Кретические баги необходимо исправить. Остальные рекомендуются к исправлению. 



