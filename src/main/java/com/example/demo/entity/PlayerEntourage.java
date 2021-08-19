package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlayerEntourage implements Serializable {
    private List<Entourage> player1Entourage;
    private List<Entourage> player2Entourage;
}
