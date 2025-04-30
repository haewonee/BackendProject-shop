package com.apple.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items",result);
        var a = new Item();
        System.out.println(a.toString());
        return "list";
    }
}

