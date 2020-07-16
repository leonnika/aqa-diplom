package ru.netology.utils;

import lombok.Data;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class SQLpart {

    private SQLpart() {
    }

    public static String getAmountInBDpayment (String payment_id) throws SQLException {
        val runner = new QueryRunner();
        val dataSQL = "SELECT amount FROM payment_entity WHERE transaction_id = ?";
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        String amountInBD = runner.query(conn, dataSQL, new ScalarHandler<>(),payment_id);
        return amountInBD;
    }

    public static String getStatusInBDpayment (String payment_id) throws SQLException {
        val runner = new QueryRunner();
        val dataSQL = "SELECT status FROM payment_entity WHERE transaction_id = ?";
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        String statusInBD = runner.query(conn, dataSQL, new ScalarHandler<>(),payment_id);
        return statusInBD;
    }

    public static String getStatusInBDcredit (String payment_id) throws SQLException {
        val runner = new QueryRunner();
        val dataSQL = "SELECT status FROM credit_request_entity WHERE bank_id = ?";
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        String statusInBD = runner.query(conn, dataSQL, new ScalarHandler<>(),payment_id);
        return statusInBD;
    }

    public static String getPayment_idInBD ()  {
        val runner = new QueryRunner();
        val dataSQL = "SELECT payment_id FROM order_entity WHERE created =(SELECT MAX(created) FROM order_entity)";

        try {
           val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
            String payment_id = runner.query(conn, dataSQL, new ScalarHandler<>());
        }

        return payment_id;
    }

    public static String getCredit_idInBD () throws SQLException {
        val runner = new QueryRunner();
        val dataSQL = "SELECT credit_id FROM order_entity WHERE created =(SELECT MAX(created) FROM order_entity)";
        val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        String credit_id = runner.query(conn, dataSQL, new ScalarHandler<>());
        return credit_id;
    }

    public static void checkAmount(String id)  {
        int expected = Integer.parseInt(DataHelper.getСurrentAmount());
        int actual = 0;
        try {
            actual = Integer.parseInt(SQLpart.getAmountInBDpayment(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    public static void checkStatusPayment(String id,String status)  {
        String actual = null;
        try {
            actual = SQLpart.getStatusInBDpayment(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(status, actual);
    }

    public static void checkStatusCredit(String id,String status)  {
        String actual = null;
        try {
            actual = SQLpart.getStatusInBDcredit(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(status, actual);
    }

    public static void checkTransaction_id() {
        val runner = new QueryRunner();
        val dataSQL = "SELECT transaction_id FROM payment_entity WHERE created =(SELECT MAX(created) FROM payment_entity)";
        String statusInBD = null;
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {

            statusInBD =  runner.query(conn, dataSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String expected = null;
        expected = getPayment_idInBD ();
        assertEquals(expected, statusInBD);
    }

    public static void checkBank_id(String credit_id)  {


    }
}
