package ru.netology.utils.ui;

import lombok.Data;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class QueriesToBD {
    private static String urlbd = System.getProperty("urlbd");
    private static String user = System.getProperty("user");
    private static String password = System.getProperty("password");

    private QueriesToBD() {
    }

    public static int getAmountInBDpayment(String payment_id) {

        val runner = new QueryRunner();
        val dataSQL = "SELECT amount FROM payment_entity WHERE transaction_id = ?";
        int amountInBD = 0;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            amountInBD = runner.query(conn, dataSQL, new ScalarHandler<>(), payment_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amountInBD;
    }

    public static String getStatusInBDpayment(String payment_id) {
        val runner = new QueryRunner();
        val dataSQL = "SELECT status FROM payment_entity WHERE transaction_id = ?";
        String statusInBD = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            statusInBD = runner.query(conn, dataSQL, new ScalarHandler<>(), payment_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusInBD;
    }

    public static String getStatusInBDcredit(String payment_id) {
        val runner = new QueryRunner();
        val dataSQL = "SELECT status FROM credit_request_entity WHERE bank_id = ?";
        String statusInBD = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            statusInBD = runner.query(conn, dataSQL, new ScalarHandler<>(), payment_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusInBD;
    }

    public static String getPayment_idInBD() {
        val runner = new QueryRunner();
        val dataSQL = "SELECT payment_id FROM order_entity WHERE created =(SELECT MAX(created) FROM order_entity)";
        String payment_id = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {

            payment_id = runner.query(conn, dataSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payment_id;
    }

    public static String getCredit_idInBD() {
        val runner = new QueryRunner();
        val dataSQL = "SELECT credit_id FROM order_entity WHERE created =(SELECT MAX(created) FROM order_entity)";
        String credit_id = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {

            credit_id = runner.query(conn, dataSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credit_id;
    }

    public static String getTransaction_id() {
        val runner = new QueryRunner();
        val dataSQL = "SELECT transaction_id FROM payment_entity WHERE created =(SELECT MAX(created) FROM payment_entity)";
        String idInBD = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {

            idInBD = runner.query(conn, dataSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idInBD;
    }

    public static String getBank_id() {
        val runner = new QueryRunner();
        val dataSQL = "SELECT bank_id FROM credit_request_entity WHERE created =(SELECT MAX(created) FROM credit_request_entity)";
        String idInBD = null;
        try (
                val conn = DriverManager.getConnection(
                        urlbd, user, password
//                val conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {

            idInBD = runner.query(conn, dataSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idInBD;
    }
}
