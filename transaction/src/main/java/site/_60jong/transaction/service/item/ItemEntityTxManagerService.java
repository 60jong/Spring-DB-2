package site._60jong.transaction.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.exception.MyException;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;

import java.util.List;

@RequiredArgsConstructor
public class ItemEntityTxManagerService implements ItemService {

    private final ItemRepository itemRepository;

    private final PlatformTransactionManager ptm;
    private static TransactionStatus status;
    private static final TransactionDefinition txDefinition = new DefaultTransactionDefinition();

    @Override
    public Item save(Item item) {
        try {
            status = ptm.getTransaction(txDefinition);

            Item save = itemRepository.save(item);

            ptm.commit(status);

            return save;
        } catch (MyException e) {
            ptm.rollback(status);
            throw e;
        }
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        try {
            status = ptm.getTransaction(txDefinition);

            itemRepository.update(itemId, updateParam);

            ptm.commit(status);
        } catch (MyException e) {
            ptm.rollback(status);
            throw e;
        }
    }

    @Override
    public Item findById(Long id) {
        try {
            status = ptm.getTransaction(txDefinition);

            Item item = itemRepository.findById(id)
                                      .orElseThrow(MyException::new);
            ptm.commit(status);
            return item;
        } catch (MyException e) {
            ptm.rollback(status);
            throw e;
        }
    }

    @Override
    public List<Item> findItems(ItemSearchCond itemSearch) {
        try {
            status = ptm.getTransaction(txDefinition);

            List<Item> items = itemRepository.findAll(itemSearch);

            ptm.commit(status);
            return items;
        } catch (MyException e) {
            ptm.rollback(status);
            throw e;
        }
    }
}
