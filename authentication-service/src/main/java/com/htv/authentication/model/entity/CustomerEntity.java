package com.htv.authentication.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {
    private String type;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
}
