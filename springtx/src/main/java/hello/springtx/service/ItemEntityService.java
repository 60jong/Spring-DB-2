package hello.springtx.service;

import hello.springtx.domain.Item;
import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.ItemSearchCond;
import hello.springtx.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceV2 implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        itemRepository.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        findById(itemId).change(
                updateParam.getItemName(),
                updateParam.getPrice(),
                updateParam.getQuantity());
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
