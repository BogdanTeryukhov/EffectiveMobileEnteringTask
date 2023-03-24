package com.example.effectivemobileentertask.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private byte logo;
    private boolean active;

    @OneToMany
    @JoinColumn
    private Set<Product> products;

    public Organization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo=" + logo +
                ", products=" + products +
                '}';
    }
}
