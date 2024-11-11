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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number", nullable = false)
    private Long orderNumber;

    @Column(name = "submitted_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private LocalDate submittedDate;

    @Column(name = "deadline_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Tirane")
    private LocalDate deadlineDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name="decline_reason")
    private String declineReason;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_shipment")
    private Shipment shipment;

}
