package com.apple.shop.sales;

import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "SalesController",description = "구매 정보 관련된 API들입니다.")
@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final SalesRepository salesRepository;

    @Operation(summary = "주문 정보 저장",description = "주문 정보를 클라에서 받아 디비에 저장합니다.")
    @PostMapping("/order")
    public String postOrder(@RequestParam String title, @RequestParam Integer price, @RequestParam Integer count,@RequestParam Long id, Authentication auth){
      salesService.orderSave(title,price,count,id,auth);
        return "list.html";
    }

    @Operation(summary = "구매내역 페이지 반환")
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
