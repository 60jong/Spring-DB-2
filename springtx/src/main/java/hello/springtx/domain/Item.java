package hello.springtx.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public static Item merge(Long id, String itemName, Integer price, Integer quantity) {
        Item item = new Item();
        item.id = id;
        item.itemName = itemName;
        item.price = price;
        item.quantity = quantity;

        return item;
    }

    public void change(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
