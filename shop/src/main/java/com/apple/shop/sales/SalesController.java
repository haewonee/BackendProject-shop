package com.apple.shop.sales;

import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final SalesRepository salesRepository;
    @PostMapping("/order")
    public String postOrder(@RequestParam String title, @RequestParam Integer price, @RequestParam Integer count,@RequestParam Long id, Authentication auth){
      salesService.orderSave(title,price,count,id,auth);
        return "list.html";
    }
    @GetMapping("/order/all")
    String getOrderAll(Model model){
        List<Sales> result = salesRepository.customFindAll();
        System.out.println(result);
//        var salesDto = new SalesDto();
//        salesDto.itemName = result.get(0).getItemName();
//        salesDto.price=result.get(0).getPrice();
//        salesDto.username=result.get(0).getMember().getUsername();
        model.addAttribute("orders",result);
        return "sales.html";
    }
}
