package site._60jong.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.exception.MyException;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemEntityTxAopService implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        findById(itemId).change(updateParam.getItemName(),
                                updateParam.getPrice(),
                                updateParam.getQuantity());
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id)
                             .orElseThrow(MyException::new);
    }

    @Override
    public List<Item> findItems(ItemSearchCond itemSearch) {
        return itemRepository.findAll(itemSearch);
    }
}
