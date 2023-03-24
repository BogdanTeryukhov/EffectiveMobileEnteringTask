package com.example.effectivemobileentertask.Entity;

import lombok.Data;

import java.awt.*;
import java.util.Arrays;

@Data
public class Organization {
    private String name;
    private String description;
    private Image logo;
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
