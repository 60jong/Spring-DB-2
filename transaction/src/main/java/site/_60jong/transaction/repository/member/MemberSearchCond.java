package site._60jong.transaction.repository.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSearchCond {

    private String name;
    private Integer minMoney;
    private Integer maxMoney;
}
