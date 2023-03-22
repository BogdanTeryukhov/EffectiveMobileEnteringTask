package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.context.annotation.Bean;

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
