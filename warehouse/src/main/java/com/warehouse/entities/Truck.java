package com.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name="container_volume")
    private double containerVolume;

    @ElementCollection
    @CollectionTable(name = "truck_unavailable_dates", joinColumns = @JoinColumn(name = "truck_id"))
    @Column(name = "unavailable_date")
    private Set<LocalDate> unavailableDates = new HashSet<>();

}
