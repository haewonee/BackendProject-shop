package com.apple.shop.comment;

import com.apple.shop.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="member_id")
    private Member member;
//    private String username;

    @Column(length = 1000)
    private String content;

    private Long parentId;
}
