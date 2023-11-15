package site._60jong.transaction.repository.item.querydsl;

import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemSearchCond;

import java.util.List;

public interface ItemQueryRepository {

    List<Item> findAll(ItemSearchCond cond);

}
