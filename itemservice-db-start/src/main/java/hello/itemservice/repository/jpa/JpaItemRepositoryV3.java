package hello.itemservice.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.domain.QItem.item;

@Slf4j
@Transactional
@Repository
public class JpaItemRepositoryV3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public JpaItemRepositoryV3(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(
                queryFactory
                        .select(item)
                        .from(item)
                        .where(item.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(cond.getItemName())) {
            builder.and(item.itemName.like("%" + cond.getItemName() + "%"));
        }

        if (cond.getMaxPrice() != null) {
            builder.and(item.price.loe(cond.getMaxPrice()));
        }

        return queryFactory
                .select(item)
                .from(item)
                .where(builder)
                .fetch();
    }
}
