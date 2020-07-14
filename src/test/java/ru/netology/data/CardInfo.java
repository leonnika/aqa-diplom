package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfo {
    private String number;
    private String status;
    private String month;
    private String year;
    private int code;
    private String user;
}
