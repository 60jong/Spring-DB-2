package site._60jong.transaction.repository.item.db.jdbc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import site._60jong.transaction.domain.Item;
import site._60jong.transaction.exception.MyException;
import site._60jong.transaction.repository.item.ItemRepository;
import site._60jong.transaction.repository.item.ItemSearchCond;
import site._60jong.transaction.repository.item.ItemUpdateDto;
import site._60jong.transaction.repository.item.db.JdbcConnectionConst;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.jdbc.support.JdbcUtils.*;
import static site._60jong.transaction.repository.item.db.JdbcConnectionConst.*;

@Slf4j
public class ItemDriverManagerRepository implements ItemRepository {

    @Override
    public Item save(Item item) {
        if (item.getItemName().equals("ex")) {
            throw new MyException();
        }

        String query = "insert into item(item_name, price, quantity) values (?, ?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            setAutoCommit(con, false);

            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getItemName());
            pstmt.setInt(2, item.getPrice());
            pstmt.setInt(3, item.getQuantity());
            pstmt.executeUpdate();

            con.commit();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
            return item;
        } catch (SQLException e) {
            rollback(con);
            throw new MyException(e);
        } finally {
            setAutoCommit(con, true);
            closeResources(con, pstmt, null);
        }
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String query = "update item set item_name = ?, price = ?, quantity = ? where id = ?";

        log.info("{} / {} / {}/ {}", updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity(), itemId);
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            setAutoCommit(con, false);

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, updateParam.getItemName());
            pstmt.setInt(2, updateParam.getPrice());
            pstmt.setInt(3, updateParam.getQuantity());
            pstmt.setLong(4, itemId);
            pstmt.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new RuntimeException(e);
        } finally {
            setAutoCommit(con, true);
            closeResources(con, pstmt, null);
        }
    }

    @Override
    public Optional<Item> findById(Long id) {
        String query = "select * from item where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(creatItemFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(con, pstmt, rs);
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        List<Item> items = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = getSearchablePreparedStatement(con, cond);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(creatItemFromResultSet(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(con, pstmt, rs);
        }
    }

    private Item creatItemFromResultSet(ResultSet rs) throws SQLException {
        return Item.merge(
                rs.getLong("id"),
                rs.getString("item_name"),
                rs.getInt("price"),
                rs.getInt("quantity")
        );
    }

    private PreparedStatement getSearchablePreparedStatement(Connection con, ItemSearchCond cond) throws SQLException {
        QueryAndParams queryAndParams = buildSearchQueryAndParams(cond.getItemName(), cond.getMaxPrice());

        List<Object> params = queryAndParams.params;
        int paramSeq = 1;

        PreparedStatement pstmt = con.prepareStatement(queryAndParams.query);

        for (Object param : params) {
            pstmt.setObject(paramSeq++, param);
        }
        return pstmt;
    }

    private QueryAndParams buildSearchQueryAndParams(String itemName, Integer maxPrice) {
        StringBuilder queryBuilder = new StringBuilder("select * from item ");
        List<Object> params = new ArrayList<>();

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
        return new QueryAndParams(queryBuilder.toString(), params);
    }

    //== About Connection ==//
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private void setAutoCommit(Connection con, boolean flag) {
        try {
            con.setAutoCommit(flag);
        } catch (SQLException e) {
            throw new MyException(e);
        }
    }

    private void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new MyException(ex);
        }
    }

    private void closeResources(Connection con, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(con);
    }

    @AllArgsConstructor
    private static class QueryAndParams {
        private String query;
        private List<Object> params;
    }
}
