package site._60jong.transaction.repository.item.querydsl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemRangeSearchCond;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemQueryRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemQueryRepository itemQueryRepository;

    @Test
    void ItemSearchCond_test() {
        // given
        Item item1 = new Item("item1", 10000, 10);
        itemRepository.save(item1);

        ItemSearchCond cond = new ItemSearchCond("item", 10000);

        // when
        List<Item> items = itemQueryRepository.findAll(cond);

        // then
        assertThat(items).containsExactly(item1);
    }

    @Test
    void ItemRangeSearchCond_test() {
        // given
        Item item1 = new Item("item1", 10000, 10);
        itemRepository.save(item1);

        ItemSearchCond cond = new ItemRangeSearchCond(
                "item",
                0,
                10000,
                0,
                100);

        // when
        List<Item> items = itemQueryRepository.findAll(cond);

        // then
        assertThat(items).containsExactly(item1);
    }

    @Test
    void ItemRangeSearchCond_nullable_test() {
        // given
        Item item1 = new Item("item1", 10000, 10);
        itemRepository.save(item1);

        ItemSearchCond cond = new ItemRangeSearchCond(
                "item",
                null,
                10000,
                null,
                null);

        // when
        List<Item> items = itemQueryRepository.findAll(cond);

        // then
        assertThat(items).containsExactly(item1);
    }
}