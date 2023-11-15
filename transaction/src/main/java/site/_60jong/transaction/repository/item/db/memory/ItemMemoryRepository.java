package site._60jong.transaction.repository.item.db.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ItemMemoryRepository implements ItemRepository {

    private static final Map<Long, Item> itemStore = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        itemStore.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        findById(itemId)
                .orElseThrow()
                .change(updateParam.getItemName(),
                        updateParam.getPrice(),
                        updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(itemStore.get(id));
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        log.info("Total Items : {}", itemStore.size());
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        return itemStore.values()
                .stream()
                .filter(item -> {
                    if (StringUtils.hasText(itemName)) {
                        return item.getItemName().contains(itemName);
                    }
                    return true;
                })
                .filter(item -> {
                    if (maxPrice != null) {
                        return item.getPrice() <= maxPrice;
                    }
                    return true;
                })
                .toList();
    }
}
