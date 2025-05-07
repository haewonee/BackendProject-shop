package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public void saveItem(String title, Integer price,String imgUrl, Authentication auth){
        if(price<0){
            throw new RuntimeException("음수 안됩니다");
        }
        if(title.length ()>10){
            throw new RuntimeException("상품명이 너무 깁니다");
        }
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setUsername(auth.getName());
        item.setImgUrl(imgUrl);
        item.setCount(10);
        itemRepository.save(item);
    }

    public List<Item> getAllItems(){
        List<Item> result = itemRepository.findAll();
        return result;
    }

    public Optional<Item> getItem(Long id){
        Optional<Item> result = itemRepository.findById(id);
        return result;
    }

    public void editItem(Long id,String title,Integer price){
        if (title.length() > 100) {
            throw new IllegalArgumentException("제목은 100자 이하로 입력해주세요.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }
    public Page<Item> pageList(Integer abc){
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(abc-1,5));
        return result;
    }

    public List<Item> searchItem(String searchText){
        List<Item> result = itemRepository.findAllByTitleContains(searchText);
        return result;
    }

}
