package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.model.MarketModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MarketNativeRepositoryImpl implements MarketNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public MarketNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    select m.market_inv_id, inv.inv_user_id, inv.inv_item_id, it.item_name, m.item_price, m.market_inv_id_status from market as m left join
//    inventory as inv on m.market_inv_id = inv.inv_id left join
//    items as it on inv.inv_item_id = it.item_id;
    @Override
    public List<MarketItemUserModel> findAllItems() {
        String sql = " select m.market_inv_id, inv.inv_user_id, inv.inv_item_id, it.item_name, m.item_price, m.market_inv_id_status ";
                sql += " from market as m left join ";
                sql += " inventory as inv on m.market_inv_id = inv.inv_id left join ";
                sql += " items as it on inv.inv_item_id = it.item_id ";

        List<MarketItemUserModel> result = this.jdbcTemplate.query(sql, new RowMapper<MarketItemUserModel>() {
            @Override
            public MarketItemUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                MarketItemUserModel i = new MarketItemUserModel();
                int col = 1;

                i.setMarketInvId(rs.getInt(col++));
                i.setInvUserId(rs.getInt(col++));
                i.setInvItemId(rs.getInt(col++));
                i.setItemName(rs.getString(col++));
                i.setItemPrice(rs.getInt(col++));
                i.setMarketInvIdStatus(rs.getString(col++));

                return i;
            }
        });
        return result;

    }
}
