package com.apple.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

    @Query(value="select * from item where match(title) against(?1)",nativeQuery = true)
    List<Item> rawQuery1(String text);
}
