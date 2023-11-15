package site._60jong.transaction.repository.item.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemSearchCond;

import java.util.List;

import static site._60jong.transaction.domain.QItem.*;
import static site._60jong.transaction.repository.item.querydsl.ItemQueryRepositoryImpl.ItemExpression.*;

@RequiredArgsConstructor
@Repository
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        return queryFactory
                .select(item)
                .from(item)
                .where(
                        nameLike(cond.getItemName()),
                        priceBetween(cond.getMinPrice(), cond.getMaxPrice()),
                        quantityBetween(cond.getMinQuantity(), cond.getMaxQuantity()))
                .fetch();
    }

    static class ItemExpression {

        static BooleanExpression nameLike(String name) {
            return StringUtils.hasText(name) ? item.itemName.like(name) : null ;
        }

        static BooleanExpression priceBetween(Integer min, Integer max) {
            return (min == null && max == null) ? null : item.price.between(min, max);
        }

        static BooleanExpression quantityBetween(Integer min, Integer max) {
            return (min == null && max == null) ? null : item.quantity.between(min, max);
        }
    }
}
