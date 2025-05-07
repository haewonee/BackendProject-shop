package com.apple.shop.comment;

import com.apple.shop.item.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Scanner;

@Tag(name="CommentController",description = "리뷰 관련된 API들 입니다.")
@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @Operation(summary = "댓글 저장", description = "유저에게 댓글을 받아 디비에 저장")
    @PostMapping("/comment")
    public String postComment(@RequestParam String content,
                              @RequestParam Long parent, Authentication auth){
        commentService.saveComment(content,parent,auth);
        return "redirect:/list";
    }

}
