package com.apple.shop.sales;

import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void orderSave(String title, Integer price, Integer count,Long id, Authentication auth) {

        var result = itemRepository.findById(id);
        if(result.isPresent()){
            var item = result.get();
            item.setCount(item.getCount()-1);
            itemRepository.save(item);
        }

        Sales sales = new Sales();
        sales.setItemName(title);
        sales.setPrice(price);
        sales.setCount(count);
        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
        salesRepository.save(sales);
    }
}