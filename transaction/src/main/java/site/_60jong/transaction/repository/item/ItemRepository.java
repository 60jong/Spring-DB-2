package site._60jong.transaction.repository.item;


import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.querydsl.ItemQueryRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends ItemQueryRepository {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

}
