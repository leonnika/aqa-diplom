package ru.netology.utils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLpart {

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
}
