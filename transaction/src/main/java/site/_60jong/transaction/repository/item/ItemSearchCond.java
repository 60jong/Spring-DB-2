package site._60jong.transaction.repository.item;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemSearchCond {

    private String itemName;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minQuantity;
    private Integer maxQuantity;
}
