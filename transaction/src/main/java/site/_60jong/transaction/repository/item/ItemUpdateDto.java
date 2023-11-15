package site._60jong.transaction.repository.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemUpdateDto {

    private String itemName;
    private Integer price;
    private Integer quantity;
}
