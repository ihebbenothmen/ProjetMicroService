package com.example.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="Forum")
public class Forum implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private LocalDateTime date;
    private String titre;
    private String publication;




















}
