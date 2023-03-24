package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String header;
    private Date date;
    private String text;

    public Notification(String header, String text) {
        this.header = header;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
