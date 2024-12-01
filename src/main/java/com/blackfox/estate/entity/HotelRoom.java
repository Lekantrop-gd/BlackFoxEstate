package com.blackfox.estate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @Min(value = 0, message = "Price must be a positive number")
    private double price;

    @OneToMany(mappedBy = "hotelRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
