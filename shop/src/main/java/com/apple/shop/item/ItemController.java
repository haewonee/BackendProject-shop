package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name="ItemController", description="상품 관련된 API들 입니다.")
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentService commentService;

    @Operation(summary = "모든 상품 리스트 반환", description = "디비에 담긴 모든 상품을 리스트에 담아 클라에 보내줍니다.")
    @GetMapping("/list")
    public String list(Model model){
        List<Item> result = itemService.getAllItems();
        model.addAttribute("items",result);
        return "redirect:/list/page/1";
    }

    @Operation(summary = "상품 작성 화면" , description = "write.html을 클라에 반환합니다.")
    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @Operation(summary = "상품 추가",description = "상품 정보를 클라에서 받아 디비에 저장합니다.")
    @PostMapping("/add")
    public String addPost(@RequestParam String title, @RequestParam Integer price, @RequestParam String imgUrl, Authentication auth){
        itemService.saveItem(title,price,imgUrl,auth);
        return "redirect:/list";
    }

    @Operation(summary = "상품 상세 페이지 반환")
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

    @Operation(summary = "상품 수정 페이지 반환", description = "상품의 정보를 클라에 보내줍니다.")
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

    @Operation(summary = "수정한 상품 저장",description = "수정 정보를 클라에서 받아 디비에 반영합니다.")
    @PostMapping("/edit")
    public String editPost(@RequestParam Long id,@RequestParam String title, @RequestParam Integer price){
        itemService.editItem(id,title,price);
        return "redirect:/list";
    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id){
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @Operation(summary = "페이지별 list페이지 반환")
    @GetMapping("/list/page/{abc}")
    public String getListPage(@PathVariable Integer abc, Model model){
        Page<Item> result = itemService.pageList(abc);
        Integer count = result.getTotalPages();
        model.addAttribute("items",result);
        model.addAttribute("count",count);
        return "list.html";
    }

    @Operation(summary = "사진 저장을 위한 API. PresignedUrl을 생성")
    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename){
        var result =s3Service.createPresignedUrl("test/"+filename);
        System.out.println(result);
        return result;
    }

    @Operation(summary = "사용자 검색어를 토대로 검색, 결과 반환")
    @PostMapping("/search")
    public String postSearch(@RequestParam String searchText,Model model){
        var result = itemRepository.rawQuery1(searchText);
        model.addAttribute("items",result);
        System.out.println(result);
        return "searchResult.html";
    }

}

