package com.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "shipment_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private LocalDate shipmentDate;

    @Column(name = "delivery_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private LocalDate deliveryDate;
}
