package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.InventoryModel;
import th.co.priorsolution.project.wonderworld.model.ItemModel;
import th.co.priorsolution.project.wonderworld.repository.InventoryNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class InventoryNativeRepositoryImpl implements InventoryNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public InventoryNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InventoryModel> findAllInventory() {
        String sql = " select inv_id, inv_user_id, inv_item_id from items";

        List<InventoryModel> result = this.jdbcTemplate.query(sql, new RowMapper<InventoryModel>() {
            @Override
            public InventoryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                InventoryModel i = new InventoryModel();
                int col = 1;
                i.setInvId(rs.getInt(col++));
                i.setInvUserId(rs.getInt(col++));
                i.setInvItemId(rs.getInt(col++));

                return i;
            }
        });
        return result;

    }

    @Override
    public int insertManyInventory(List<InventoryModel> inventoryModels) {
        List<Object> paramList = new ArrayList<>();
        String sql = " insert into items (inv_id, inv_user_id, inv_item_id) values ";

        StringJoiner stringJoiner = new StringJoiner(",");
        for (InventoryModel i: inventoryModels) {
            String value = "( (select ifnull(max(item_id)+1, 1) from items i), ?, ?)";

            paramList.add(i.getInvUserId());
            paramList.add(i.getInvItemId());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }

}
