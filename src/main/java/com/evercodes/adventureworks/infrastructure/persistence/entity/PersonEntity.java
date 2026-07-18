package com.evercodes.adventureworks.infrastructure.persistence.entity;

import com.evercodes.adventureworks.domain.model.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Person", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @Column(name = "BusinessEntityID")
    private Integer businessEntityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PersonType", columnDefinition = "nchar(2)")
    private PersonType personType;

    @Column(name = "NameStyle", columnDefinition = "bit")
    private boolean nameStyle;

    @Column(name = "Title", length = 8)
    private String title;

    @Column(name = "FirstName", columnDefinition = "nvarchar(50)")
    private String firstName;

    @Column(name = "MiddleName", columnDefinition = "nvarchar(50)")
    private String middleName;

    @Column(name = "LastName", columnDefinition = "nvarchar(50)")
    private String lastName;

    @Column(name = "Suffix", length = 10)
    private String suffix;

    @Column(name = "EmailPromotion")
    private int emailPromotion;
}
