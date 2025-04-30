package com.apple.shop;

import jakarta.persistence.*;
import lombok.ToString;

@ToString
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //알아서 1씩 증가시켜준다.

    private String title;

    private Integer price;
}

