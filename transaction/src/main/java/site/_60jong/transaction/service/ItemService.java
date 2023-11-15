package site._60jong.transaction.service;

import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;

import java.util.List;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Item findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);

}
