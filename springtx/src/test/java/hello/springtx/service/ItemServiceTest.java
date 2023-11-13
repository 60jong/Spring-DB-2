package hello.springtx.service;

import hello.springtx.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("item 저장 성공")
    void save_success() {
        // given
        Item item = new Item("item1", 1000, 10);

        // when
        Item saveItem = itemService.save(item);
        Item findItem = itemService.findById(saveItem.getId());

        // then
        assertThat(saveItem).isEqualTo(findItem);
    }
}
