package com.example.effectivemobileentertask.Entity;

import lombok.Data;

import java.util.Arrays;

@Data
public class Organization {
    private String name;
    private String description;
    private int logo;
    private Product[] products;

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo=" + logo +
                ", products=" + Arrays.toString(products) +
                '}';
    }
}
