package site._60jong.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemDBService implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        itemRepository.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.update(itemId, updateParam);
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id)
                             .orElseThrow();
    }

    @Override
    public List<Item> findItems(ItemSearchCond itemSearch) {
        return itemRepository.findAll(itemSearch);
    }

}
