package ru.netology.utils.ui;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.Data;
import ru.netology.data.CardInfo;
import ru.netology.data.CardJSON;
import ru.netology.data.DateCard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class DataHelper {

    private DataHelper() {
    }

    public static CardJSON getValidCardJsonAPPROVED() {
        return new CardJSON("4444 4444 4444 4441", "APPROVED");
    }

    public static CardJSON getValidCardJsonDECLINED() {
        return new CardJSON("4444 4444 4444 4442", "DECLINED");
    }

    public static CardInfo getValidCardInfoAPPROVED() {
        Faker faker = new Faker(new Locale("en"));
        String number = "4444 4444 4444 4441";
        String status = "APPROVED";
        String year = DataHelper.getValidDateMM().getYear();
        String month = DataHelper.getValidDateMM().getMonth();
        String user = faker.name().firstName() + " " + faker.name().lastName();
        int code = Integer.parseInt(faker.regexify("[1-9]{3}"));
        return new CardInfo(number, status, month, year, code, user);
    }

    public static CardInfo getValidCardInfoDECLINED() {
        Faker faker = new Faker(new Locale("en"));
        String number = "4444 4444 4444 4442";
        String status = "DECLINED";
        String year = DataHelper.getValidDateMM().getYear();
        String month = DataHelper.getValidDateMM().getMonth();
        String user = faker.name().firstName() + faker.name().lastName();
        int code = Integer.parseInt(faker.regexify("[0-9]{3}"));
        return new CardInfo(number, status, month, year, code, user);
    }

    public static String getInvalidCardNumber(int leghtString) {
        Faker faker = new Faker(new Locale("ru"));
        int i = 0;
        String result = "";
        while (i < leghtString) {
            result = result + faker.regexify("[0-9]{1}");
            i++;
        }

        return result;
    }

    // метод для получения валидной даты. .
    public static DateCard getValidDateMM() {
        //Из текущей даты берем год и месяц
        LocalDate currentDate = LocalDate.now();
        String currentYear = currentDate.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM", new Locale("ru")));
        //допустим что карта выдаа на максимальный срок
        int intervalYear = 5;
        // выбираем рандомный валидный год из интервала от текущего года до иаксимального т.е. плюс 5 лет
        int randomIndexYear = (int) (Math.random() * intervalYear);
        LocalDate ramdomValidYear = currentDate.plusYears(randomIndexYear);
        String ramdomYearStr = ramdomValidYear.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        //массив из месяцев в формате ММ
        int monthNumber = 12;
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        //рандовный месяц выбираем из возможных
        int randomIndex = (int) (Math.random() * monthNumber);
        String randomMonth = month[randomIndex];
        // если рандомный год равен текущему, то валидным будет только месяц больше и равен текущему
        // (если меньше текущего т.е раньше то карта уже просрочена и месяц не будет валидным)
        //выбираем месяц пока эту условие не выполнится
        if (Integer.parseInt(ramdomYearStr) == Integer.parseInt(currentYear)) {
            while (Integer.parseInt(randomMonth) < Integer.parseInt(currentMonth)) {
                randomIndex = (int) (Math.random() * monthNumber);
                randomMonth = month[randomIndex];
            }
        }
        //если год равен текущец плюс пять, то валидным будет месяц меньше текущего
        // иначе срок карты не на пять лет а больше. Перебираем месяц пока это не выполнится
        if (Integer.parseInt(ramdomYearStr) == (Integer.parseInt(currentYear)) + intervalYear) {
            while (Integer.parseInt(randomMonth) > Integer.parseInt(currentMonth)) {
                randomIndex = (int) (Math.random() * monthNumber);
                randomMonth = month[randomIndex];
            }
        }
        return new DateCard(randomMonth, ramdomYearStr);
    }

    // логика метода аналогично предыдущему,
    // только пользователь может ввести месяц и как однозначное число, что тоже допустимо
    public static DateCard getValidDateM() {
        LocalDate currentDate = LocalDate.now();
        String currentYear = currentDate.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM", new Locale("ru")));
        int intervalYear = 5;
        int randomIndexYear = (int) (Math.random() * intervalYear);
        LocalDate ramdomValidYear = currentDate.plusYears(randomIndexYear);
        String ramdomYearStr = ramdomValidYear.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        int monthNumber = 9;
        String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int randomIndex = (int) (Math.random() * monthNumber);
        String randomMonth = month[randomIndex];
        if (Integer.parseInt(ramdomYearStr) == Integer.parseInt(currentYear)) {
            while (Integer.parseInt(randomMonth) < Integer.parseInt(currentMonth)) {
                randomIndex = (int) (Math.random() * monthNumber);
                randomMonth = month[randomIndex];
            }
        }
        if (Integer.parseInt(ramdomYearStr) == (Integer.parseInt(currentYear)) + intervalYear) {
            while (Integer.parseInt(randomMonth) > Integer.parseInt(currentMonth)) {
                randomIndex = (int) (Math.random() * monthNumber);
                randomMonth = month[randomIndex];
            }
        }
        return new DateCard(randomMonth, ramdomYearStr);
    }

    //метод получения невалидной даты(существующая дата) при которой срок карты истек. Т.Е. год меньшего текущего,
    // либо если равен текущему, но месяц прошедший текущему
    // при такой дате, ситемы выдает ошибку "срок действия истек"
    public static DateCard getDateIsLast() {
        LocalDate currentDate = LocalDate.now();
        String currentYear = currentDate.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        String currentMonth = currentDate.format(DateTimeFormatter.ofPattern("MM", new Locale("ru")));
        int intervalYear = Integer.parseInt(currentYear);
        int randomIndexYear = (int) (Math.random() * intervalYear);
        LocalDate ramdomYearIsLast = currentDate.minusYears(randomIndexYear);
        String ramdomYearStr = ramdomYearIsLast.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        int monthNumber = 12;
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int randomIndex = (int) (Math.random() * monthNumber);
        String randomMonth = month[randomIndex];
        if (Integer.parseInt(ramdomYearStr) == Integer.parseInt(currentYear)) {
            while (Integer.parseInt(randomMonth) > Integer.parseInt(currentMonth)) {
                randomIndex = (int) (Math.random() * monthNumber);
                randomMonth = month[randomIndex];
            }
        }
        return new DateCard(randomMonth, ramdomYearStr);
    }

    //метод получения невалидной даты
// при такой дате, ситемы выдает ошибку неверный формат"
    public static DateCard getDateInvalid() {
        Faker faker = new Faker(new Locale("ru"));
        // как вариант точно не бывает месяца число которого юольше 12
        int randomMonth = 12 + Integer.parseInt(faker.regexify("[1-9]{1}"));
        LocalDate currentDate = LocalDate.now();
        LocalDate maxValidYear = currentDate.plusYears(6);
        //   как вариант год больше текущего на 20 точно не будет валидным относительно срока действия карты
        int invalidIntervalYear = 20;
        int randomIndexYear = (int) (Math.random() * invalidIntervalYear);
        LocalDate ramdomInValidYear = currentDate.plusYears(randomIndexYear);
        String ramdomYearStr = ramdomInValidYear.format(DateTimeFormatter.ofPattern("yy", new Locale("ru")));
        return new DateCard(Integer.toString(randomMonth), ramdomYearStr);
    }

    public static String getStringIsSpecialSymbols(int leghtString) {
        String[] specialSymbols = {"!", "@", "#", "$", "%", "^", "&", "(", ")", "'", "_", "+", "?", "<"};
        int numberSpecialSymbols = 14;
        int randomIndexSpecialSymbols = (int) (Math.random() * numberSpecialSymbols);
        int i = 0;
        String result = "";
        while (i < leghtString) {
            result = result + specialSymbols[randomIndexSpecialSymbols];
            randomIndexSpecialSymbols = (int) (Math.random() * numberSpecialSymbols);
            i++;
        }
        return result;
    }

    public static String getUserShortName() {
        Faker faker = new Faker(new Locale("ru"));
        String nameStr = faker.regexify("[A-Z]{1}") + " " + faker.regexify("[A-Z]{1}");
        return nameStr;
    }

    public static String getUserLongName() {
        Faker faker = new Faker(new Locale("ru"));
        String nameStr = faker.regexify("[A-Z]{10}") + " " + faker.regexify("[A-Z]{10}");
        return nameStr;
    }

    public static String getUserVeryLongName() {
        Faker faker = new Faker(new Locale("ru"));
        String nameStr = faker.regexify("[A-Z]{11}") + " " + faker.regexify("[A-Z]{10}");
        return nameStr;
    }

    public static String getUserRuName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getUserDubleName() {
        Faker faker = new Faker(new Locale("en"));
        String nameStr = faker.name().lastName() + " " + faker.name().firstName() + "-" + faker.name().firstName();
        return nameStr;
    }

    public static String getUserDifferentCaseLettersName() {
        Faker faker = new Faker(new Locale("en"));
        String nameStr = faker.regexify("[A-Z]{6}") + " " + faker.name().firstName();
        return nameStr;
    }

    public static String getСurrentAmount() {
        SelenideElement amountElement = $$("[class='list__item']").findBy(text("руб"));
        String amount = amountElement.getText().replaceAll("[^0-9]", "") + "00";
        return amount;
    }
}
