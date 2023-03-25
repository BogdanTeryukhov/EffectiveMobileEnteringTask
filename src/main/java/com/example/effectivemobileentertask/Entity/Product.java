package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;


@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = "organization")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;
    private String description;
    private int price;
    private int numOnTheStock;

    @ManyToOne
    @JoinColumn
    private Organization organization;

    private String infoAboutSales;
    private String[] reviews;
    private String[] keyWords;
    private String[] characteristics;
    private String[] grades;


    public Product(String name, String description, int price, int numOnTheStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.numOnTheStock = numOnTheStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
