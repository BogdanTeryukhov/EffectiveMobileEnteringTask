package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


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
    private double price;
    private int numOnTheStock;
    private boolean active;

    @ManyToMany
    @JoinColumn
    private Set<User> user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Organization organization;

    private double infoAboutSales;

    private Set<String> reviews;
    private String[] keyWords;
    private String[] characteristics;

    private Set<Double> grades;

    private Date dateAfterBuying;


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
