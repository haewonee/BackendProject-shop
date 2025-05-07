package com.apple.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(indexes = @Index(columnList = "title",name = "작명"))
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; //알아서 1씩 증가시켜준다.

    private String title;

    private Integer price;

    private String username;

    private String imgUrl;

    private Integer count;
}

