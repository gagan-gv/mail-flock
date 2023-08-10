package com.example.mailflock.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "demo_booking")
public class Demos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookers_name", nullable = false)
    private String name;

    @Column(name = "bookers_email", nullable = false)
    private String emailId;

    @Column(name = "booking_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "booking_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Time time;

    @Column(name = "is_completed")
    @Builder.Default
    private boolean isCompleted = false;
}
