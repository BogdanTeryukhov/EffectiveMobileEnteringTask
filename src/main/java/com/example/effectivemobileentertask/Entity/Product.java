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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    private String description;
    private String organization;
    private int price;
    private int numOnTheStock;
    private String infoAboutSales;

    private String[] reviews;
    private String[] keyWords;
    private String[] characteristics;
    private String[] grades;

    public Product(String name, String description, String organization, int price, int numOnTheStock, String infoAboutSales, String[] reviews, String[] keyWords, String[] characteristics, String[] grades) {
        this.name = name;
        this.description = description;
        this.organization = organization;
        this.price = price;
        this.numOnTheStock = numOnTheStock;
        this.infoAboutSales = infoAboutSales;
        this.reviews = reviews;
        this.keyWords = keyWords;
        this.characteristics = characteristics;
        this.grades = grades;
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
                ", keyWords='" + keyWords + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", grades='" + grades + '\'' +
                '}';
    }
}
