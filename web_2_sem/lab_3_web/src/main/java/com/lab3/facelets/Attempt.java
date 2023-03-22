package com.lab3.facelets;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Attempt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int attempt;
    @Column
    private double x;
    @Column
    private double y;
    @Column
    private double r;
    @Column
    private boolean hit;
    @Column
    private LocalDateTime startTime;
}
