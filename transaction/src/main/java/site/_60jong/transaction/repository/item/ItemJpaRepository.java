package site._60jong.transaction.repository.item;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.exception.MyException;
import site._60jong.transaction.repository.item.querydsl.ItemQueryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ItemJpaRepository implements ItemRepository {

    private final EntityManager em;
    private final ItemQueryRepository itemQueryRepository;

    @Transactional
    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Transactional
    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = findById(itemId)
                .orElseThrow(MyException::new);

        item.change(updateParam.getItemName(),
                    updateParam.getPrice(),
                    updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        return itemQueryRepository.findAll(cond);
    }

    /*
    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String jpql = "select i from Item i";
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')";
            andFlag = true;
        }
        if (maxPrice != null) {if (andFlag) {
            jpql += " and";
        }
            jpql += " i.price <= :maxPrice";
        }
        TypedQuery<Item> query = em.createQuery(jpql, Item.class);
        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        System.out.println();
        return query.getResultList();
    }
     */
}
