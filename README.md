# Тестирование сценария покупки тура через комплексный веб-сервис, взаимодействующего с СУБД и API Банка.

## Сборка проекта на Appveyor CI
[![Build status](https://ci.appveyor.com/api/projects/status/54jnx0wsn98v59x8/branch/master?svg=true)](https://ci.appveyor.com/project/leonnika/aqa-diplom/branch/master)

## Документация

1. План автоматизации - [Plan.md](https://github.com/leonnika/aqa-diplom/blob/master/docs/Plan.md)
2. Отчётные документы по итогам тестирования -  [Report.md](https://github.com/leonnika/aqa-diplom/blob/master/docs/Report.md)
3. Отчётные документы по итогам автоматизации - [Summary.md](https://github.com/leonnika/aqa-diplom/blob/master/docs/Summary.md)

## Краткое описание работы веб-сервиса
Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты
Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам (через симулятор, который написан на Node.js, поэтому для запуска использован Docker):

* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Симулятор позволяет для заданного набора карт генерировать предопределённые ответы.

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

Заявлена поддержка двух СУБД:

* MySQL
* PostgreSQL


## Руководство использования

* Для запуска контейнеров выполните:

```
docker-compose up
```

* Для запуска приложения c mysql выполните:

```
java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar 

```
* Для запуска приложения c psql выполните:

```
java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar ./artifacts/aqa-shop.jar 

```

* Для запуска тестов c mysql:

```
gradlew test -Dselenide.headless=true -Durlbd=jdbc:mysql://localhost:3306/app --info
```
* Для запуска тестов c psql:

```
gradlew test -Dselenide.headless=true -Durlbd=jdbc:postgresql://localhost:5432/app --info
```


