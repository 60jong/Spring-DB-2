package hello.springtx.service;

import hello.springtx.domain.Item;
import hello.springtx.exception.MyException;
import hello.springtx.repository.ItemRepository;
import hello.springtx.repository.ItemSearchCond;
import hello.springtx.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
