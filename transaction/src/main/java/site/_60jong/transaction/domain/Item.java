package site._60jong.transaction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item compare)) {
            return false;
        }

        return this.id.equals(compare.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
