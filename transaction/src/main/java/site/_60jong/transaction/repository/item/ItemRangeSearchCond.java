package site._60jong.transaction.repository.item;

import lombok.Getter;

@Getter
public class ItemRangeSearchCond extends ItemSearchCond {

    private Integer minPrice;
    private Integer minQuantity;
    private Integer maxQuantity;

    public ItemRangeSearchCond(String itemName, Integer minPrice, Integer maxPrice, Integer minQuantity, Integer maxQuantity) {
        super(itemName, maxPrice);
        this.minPrice = minPrice;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }
}
