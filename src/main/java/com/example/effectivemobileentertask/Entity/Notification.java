package com.example.effectivemobileentertask.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    private String header;
    private Date date;
    private String text;

    @Override
    public String toString() {
        return "Notification{" +
                "header='" + header + '\'' +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
