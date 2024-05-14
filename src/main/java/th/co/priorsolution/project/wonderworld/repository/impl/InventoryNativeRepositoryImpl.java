package th.co.priorsolution.project.wonderworld.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
import th.co.priorsolution.project.wonderworld.model.InventoryModel;
import th.co.priorsolution.project.wonderworld.repository.InventoryNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryNativeRepositoryImpl implements InventoryNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public InventoryNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InventoryItemEachUserModel> findAllInventory() {

        String sql = " SELECT inv.inv_id, inv.inv_user_id, u.user_name, u.user_atk, u.user_balance, inv.inv_item_id, it.item_name ";
        sql +=  " FROM inventory as inv left join items as it  on inv.inv_item_id = it.item_id ";
        sql += " left join users as u on inv.inv_user_id = u.user_id";

        List<InventoryItemEachUserModel> result = this.jdbcTemplate.query(sql, new RowMapper<InventoryItemEachUserModel>() {
            @Override
            public InventoryItemEachUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                InventoryItemEachUserModel i = new InventoryItemEachUserModel();
                int col = 1;

                i.setInvId(rs.getInt(col++));
                i.setInvUserId(rs.getInt(col++));
                i.setUserName(rs.getString(col++));
                i.setUserAtk(rs.getInt(col++));
                i.setUserBalance(rs.getInt(col++));
                i.setInvItemId(rs.getInt(col++));
                i.setItemName(rs.getString(col++));

                return i;
            }
        });
        return result;
    }

    @Override
    public List<InventoryItemEachUserModel> findInventoryUserId(int userId) {
        List<Object> paramList = new ArrayList<>();
//        String sql = " select inv_id, inv_user_id, inv_item_id from inventory where inv_user_id = ? ";
        String sql = " SELECT inv.inv_id, inv.inv_user_id, u.user_name, u.user_atk, u.user_balance, inv.inv_item_id, it.item_name ";
                sql +=  " FROM inventory as inv left join items as it  on inv.inv_item_id = it.item_id ";
                sql += " left join users as u on inv.inv_user_id = u.user_id where inv.inv_user_id = ?";

        paramList.add(userId);

        List<InventoryItemEachUserModel> result = this.jdbcTemplate.query(sql, new RowMapper<InventoryItemEachUserModel>() {
            @Override
            public InventoryItemEachUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                //model for left join
                InventoryItemEachUserModel i = new InventoryItemEachUserModel();
                int col = 1;

                i.setInvId(rs.getInt(col++));
                i.setInvUserId(rs.getInt(col++));
                i.setUserName(rs.getString(col++));
                i.setUserAtk(rs.getInt(col++));
                i.setUserBalance(rs.getInt(col++));
                i.setInvItemId(rs.getInt(col++));
                i.setItemName(rs.getString(col++));

                return i;
            }
        }, paramList.toArray());
        return result;
    }

    @Override
    public void deleteInventoryIdByNativeSql(InventoryModel inventoryModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " delete from inventory where inv_id = ? ";
        if (StringUtils.isNotEmpty(String.valueOf(inventoryModel.getInvId()))){
            paramList.add(inventoryModel.getInvId());
            this.jdbcTemplate.update(sql, paramList.toArray());
        }
    }
}
