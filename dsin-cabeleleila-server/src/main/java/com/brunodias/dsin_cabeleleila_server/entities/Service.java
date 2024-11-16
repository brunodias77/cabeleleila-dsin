package com.brunodias.dsin_cabeleleila_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.mapping.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Service extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @JsonBackReference
    @Transient
    @ManyToMany(mappedBy = "services")
    private Set<Appointment> appointments = new HashSet<>();

}
