package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentService commentService;
    @GetMapping("/list")
    public String list(Model model){
        List<Item> result = itemService.getAllItems();
        model.addAttribute("items",result);
        return "redirect:/list/page/1";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title, @RequestParam Integer price, @RequestParam String imgUrl, Authentication auth){
        itemService.saveItem(title,price,imgUrl,auth);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,Model model) {
        Optional<Item> result = itemService.getItem(id);
        List<Comment> comment = commentService.getComment(id);
        if(result.isPresent()){
            model.addAttribute("data",result.get());
            model.addAttribute("comment",comment);
            return "detail.html";

        }
        else{
            return "redirect:/list";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
       Optional<Item> result = itemService.getItem(id);
       if(result.isPresent()){
        model.addAttribute("data",result.get());
        return "edit.html";}
       else{
           return "redirect:/list";
       }
    }

    @PostMapping("/edit")
    public String editPost(@RequestParam Long id,@RequestParam String title, @RequestParam Integer price){
        itemService.editItem(id,title,price);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id){
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/list/page/{abc}")
    public String getListPage(@PathVariable Integer abc, Model model){
        Page<Item> result = itemService.pageList(abc);
        Integer count = result.getTotalPages();
        model.addAttribute("items",result);
        model.addAttribute("count",count);
        return "list.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename){
        var result =s3Service.createPresignedUrl("test/"+filename);
        System.out.println(result);
        return result;
    }

    @PostMapping("/search")
    public String postSearch(@RequestParam String searchText,Model model){
        var result = itemRepository.rawQuery1(searchText);
        model.addAttribute("items",result);
        System.out.println(result);
        return "searchResult.html";
    }

}

