package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JdbcItemRepositoryV1 implements ItemRepository {

    private final JdbcTemplate template;

    public JdbcItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String query = "insert into item(item_name, price, quantity) values (?,?,?);";
        template.update(query, item.getItemName(), item.getPrice(), item.getQuantity());
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String query = "update item set item_name = ?, price = ?, quantity = ? where id = ?";

        template.update(query,
                updateParam.getItemName(),
                updateParam.getPrice(),
                updateParam.getQuantity(),
                itemId);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String query = "select * from item where id = ?";

        Item findItem = template.queryForObject(query, itemRowMapper(), id);

        return Optional.of(findItem); // queryForObject
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            Item item = new Item(
                    rs.getString("item_name"),
                    rs.getInt("price"),
                    rs.getInt("quantity")
            );
            item.setId(rs.getLong("id"));
            return item;
        };
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        StringBuilder queryBuilder = new StringBuilder("select * from item ");
        List<Object> params = new ArrayList<>();

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            queryBuilder.append(" where ");

            boolean andFlag = false;

            if (StringUtils.hasText(itemName)) {
                queryBuilder.append(" item_name like concat('%', ?, '%') ");
                params.add(itemName);
                andFlag = true;
            }

            if (maxPrice != null) {
                if (andFlag) {
                    queryBuilder.append(" and ");
                }
                queryBuilder.append(" price <= ? ");
                params.add(maxPrice);
            }
        }
        log.info("search query : {}", queryBuilder.toString());
        return template.query(queryBuilder.toString(), itemRowMapper(), params.toArray());
    }
}
