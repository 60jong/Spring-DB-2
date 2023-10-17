package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * NamedParameterJdbcTemplate
 */
@Slf4j
public class JdbcItemRepositoryV2 implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String query = "insert into item(item_name, price, quantity) " +
                "values (:itemName, :price, :quantity);";
        SqlParameterSource source = new BeanPropertySqlParameterSource(item);
        template.update(query, source);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String query = "update item " +
                "set item_name = :item_name, " +
                "price = :price, " +
                "quantity = :quantity " +
                "where id = :id";

        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("item_name", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
        template.update(query, source);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String query = "select * from item where id = ?";

        Item findItem = template.queryForObject(query, Map.of("id", id), itemRowMapper());

        return Optional.of(findItem); // queryForObject
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        StringBuilder queryBuilder = new StringBuilder("select * from item ");

        Map<String, Object> paramMap = new HashMap<>();

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            queryBuilder.append(" where ");

            boolean andFlag = false;

            if (StringUtils.hasText(itemName)) {
                queryBuilder.append(" item_name like concat('%', :itemName, '%') ");
                paramMap.put("itemName", itemName);
                andFlag = true;
            }

            if (maxPrice != null) {
                if (andFlag) {
                    queryBuilder.append(" and ");
                }
                queryBuilder.append(" price <= :maxPrice ");
                paramMap.put("maxPrice", maxPrice);
            }
        }
        log.info("search query : {}", queryBuilder.toString());
        return template.query(queryBuilder.toString(), paramMap, itemRowMapper());
    }
}
