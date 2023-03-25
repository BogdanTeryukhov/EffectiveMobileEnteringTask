package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class SalesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinColumn
    private Set<Product> products;

    private int volumeOfTheSale;

    private int durationInDays;

    private Date startOfTheSale;

    public SalesInfo(int volumeOfTheSale, int durationInDays) {
        this.volumeOfTheSale = volumeOfTheSale;
        this.durationInDays = durationInDays;
    }
}
