package site._60jong.transaction.repository.item.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.repository.item.ItemRangeSearchCond;
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
        BooleanBuilder builder = getBooleanBuilder(cond);

        return queryFactory
                .select(item)
                .from(item)
                .where(builder)
                .fetch();
    }

    private static BooleanBuilder getBooleanBuilder(ItemSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();

        if (cond instanceof ItemRangeSearchCond rangeCond) {
            System.out.println("itemRange");
            builder
                    .and(nameLike(rangeCond.getItemName()))

                    .and(goePrice(rangeCond.getMinPrice()))
                    .and(loePrice(rangeCond.getMaxPrice()))

                    .and(goeQuantity(rangeCond.getMinPrice()))
                    .and(loeQuantity(rangeCond.getMaxQuantity()));
            return builder;
        }
        System.out.println("itmeSearch");
        // ItemSearchCond
        builder
                .and(nameLike(cond.getItemName()))
                .and(loePrice(cond.getMaxPrice()));

        return builder;
    }

    static class ItemExpression {

        static BooleanExpression nameLike(String name) {
            return StringUtils.hasText(name) ? item.itemName.like("%" + name + "%") : null ;
        }

        static BooleanExpression goePrice(Integer min) {
            return min == null ? null : item.price.goe(min);
        }

        static BooleanExpression loePrice(Integer max) {
            return max == null ? null : item.price.loe(max);
        }

        static BooleanExpression goeQuantity(Integer min) {
            return min == null ? null : item.quantity.goe(min);
        }

        static BooleanExpression loeQuantity(Integer max) {
            return max == null ? null : item.quantity.loe(max);
        }
    }
}
