package com.apple.shop.comment;

import com.apple.shop.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public String postComment(@RequestParam String content,
                              @RequestParam Long parent, Authentication auth){
        commentService.saveComment(content,parent,auth);
        return "redirect:/list";
    }

}
