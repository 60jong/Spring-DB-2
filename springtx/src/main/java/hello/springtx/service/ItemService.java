package hello.springtx.service;

import hello.springtx.domain.Item;
import hello.springtx.repository.ItemSearchCond;
import hello.springtx.repository.ItemUpdateDto;

import java.util.List;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Item findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);

}
