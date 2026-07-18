package com.evercodes.adventureworks.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BusinessEntity", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessEntityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;
}
