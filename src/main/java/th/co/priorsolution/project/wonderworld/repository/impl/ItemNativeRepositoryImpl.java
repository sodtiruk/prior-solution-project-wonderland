package th.co.priorsolution.project.wonderworld.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.ItemModel;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.repository.ItemNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class ItemNativeRepositoryImpl implements ItemNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ItemModel> findAllItems() {
        String sql = " select item_id, item_name from items";

        List<ItemModel> result = this.jdbcTemplate.query(sql, new RowMapper<ItemModel>() {
            @Override
            public ItemModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                ItemModel i = new ItemModel();
                int col = 1;
                i.setItemId(rs.getInt(col++));
                i.setItemName(rs.getString(col++));

                return i;
            }
        });
        return result;
    }

    @Override
    public int insertManyItems(List<ItemModel> itemModels) {
        List<Object> paramList = new ArrayList<>();
        String sql = " insert into items (item_id, item_name) values ";

        StringJoiner stringJoiner = new StringJoiner(",");
        for (ItemModel i: itemModels) {
            String value = "( (select ifnull(max(item_id)+1, 1) from items i), ?)";

            paramList.add(i.getItemName());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }

    @Override
    public int updateItemByNativeSql(ItemModel itemModel) {
        List<Object> paramList = new ArrayList<>();

        StringJoiner columnSql = new StringJoiner(",");
        String sql = " update items ";
        sql +=       " set ";

        if (StringUtils.isNotEmpty(itemModel.getItemName()) && itemModel.getItemName() != "0"){
            columnSql.add(" item_name = ? ");
            paramList.add(itemModel.getItemName());
        }

        sql += columnSql.toString();
        sql += " where item_id = ? ";
        paramList.add(itemModel.getItemId());

        int numberColumnUpdated = this.jdbcTemplate.update(sql, paramList.toArray());
        return numberColumnUpdated;
    }

    @Override
    public void deleteItemIdByNativeSql(ItemModel itemModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " delete from items where item_id = ? ";
        if (StringUtils.isNotEmpty(String.valueOf(itemModel.getItemId()))){
            paramList.add(itemModel.getItemId());
            this.jdbcTemplate.update(sql, paramList.toArray());
        }
    }
}
