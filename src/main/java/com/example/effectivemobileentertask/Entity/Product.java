package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;
    private String description;
    private int price;
    private int numOnTheStock;


    private String infoAboutSales;
    private String organization;
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
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", organization='" + organization + '\'' +
                ", price=" + price +
                ", numOnTheStock=" + numOnTheStock +
                ", infoAboutSales='" + infoAboutSales + '\'' +
                ", reviews='" + Arrays.toString(reviews) + '\'' +
                ", keyWords='" + Arrays.toString(keyWords) + '\'' +
                ", characteristics='" + Arrays.toString(characteristics) + '\'' +
                ", grades='" + Arrays.toString(grades) + '\'' +
                '}';
    }
}
