package hello.springtx.repository;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemSearchCond {

    private String itemName;
    private Integer maxPrice;
}
