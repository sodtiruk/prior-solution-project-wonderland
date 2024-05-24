package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public MarketItemUserModel sellItemUser(Map<String, Object> data) {
        List<Object> paramListForInsert = new ArrayList<>();

        Object marketIdSell = data.get("marketInvId");
        Object itemPrice = data.get("itemPrice");
        Object marketStatus = "selling";

        String selledItemOnMarketSql = " insert into market (market_inv_id, item_price, market_inv_id_status) values (?, ?, ?) ";

        String getItemOnMarketSql = " select m.market_inv_id, inv.inv_user_id, inv.inv_item_id, it.item_name, m.item_price, m.market_inv_id_status ";
        getItemOnMarketSql += " from market as m left join ";
        getItemOnMarketSql += " inventory as inv on m.market_inv_id = inv.inv_id left join ";
        getItemOnMarketSql += "  items as it on inv.inv_item_id = it.item_id where market_inv_id = ? ";

        paramListForInsert.add(marketIdSell);
        paramListForInsert.add(itemPrice);
        paramListForInsert.add(marketStatus);

        this.jdbcTemplate.update(selledItemOnMarketSql, paramListForInsert.toArray());

        MarketItemUserModel marketItemUserModel = this.jdbcTemplate.queryForObject(getItemOnMarketSql,
                new Object[]{(int)marketIdSell}, (rs, rowNum) -> {

            MarketItemUserModel m = new MarketItemUserModel();

            m.setMarketInvId(rs.getInt("market_inv_id"));
            m.setInvUserId(rs.getInt("inv_user_id"));
            m.setInvItemId(rs.getInt("inv_item_id"));
            m.setItemName(rs.getString("item_name"));
            m.setItemPrice(rs.getInt("item_price"));
            m.setMarketInvIdStatus(rs.getString("market_inv_id_status"));

            return m;
        });

        return marketItemUserModel;
    }

    @Override
    public String buyItemInMarket(Map<String, Object> data) {
        Object userBuyerId = data.get("userId");
        Object itemMarketId = data.get("marketInvId");

        //check if user have money enough
        String userBalanceSql = " select user_balance from users where user_id = ? ";
        String itemPriceSql = " select item_price from market where market_inv_id = ? ";

        Object userBalance =  this.jdbcTemplate.queryForObject(userBalanceSql, new Object[]{userBuyerId}, Integer.class);
        Object itemPrice =  this.jdbcTemplate.queryForObject(itemPriceSql, new Object[]{itemMarketId}, Integer.class);

        if ((int)userBalance >= (int)itemPrice) {
            // you can buy
            // update balance user
            List<Object> paramForUpdateBalance = new ArrayList<>();
            Object balanceLeft = (int)userBalance - (int)itemPrice;

            paramForUpdateBalance.add(balanceLeft);
            paramForUpdateBalance.add(userBuyerId);

            String updateBalanceUserSql = " update users set user_balance = ? where user_id = ? ";
            this.jdbcTemplate.update(updateBalanceUserSql, paramForUpdateBalance.toArray());

            // update market
            Object marketInvIdStatus = "selled";
            String updateMarketItemSql = " update market set market_inv_id_status = ? where market_inv_id = ? ";
            List<Object> paramForUpdateMarketItem = new ArrayList<>();
            paramForUpdateMarketItem.add(marketInvIdStatus);
            paramForUpdateMarketItem.add(itemMarketId);
            this.jdbcTemplate.update(updateMarketItemSql, paramForUpdateMarketItem.toArray());

            // update change inventory
            String updateInventoryUserSql = " update inventory set inv_user_id = ? where inv_id = ? ";
            List<Object> paramForUpdateInventory = new ArrayList<>();
            paramForUpdateInventory.add(userBuyerId);
            paramForUpdateInventory.add(itemMarketId);
            this.jdbcTemplate.update(updateInventoryUserSql, paramForUpdateInventory.toArray());

            // return query data inventory
            return "You buy item successfully";
        }else {
            // you can't buy
            return "You can't buy item but money not enough";
        }
    }
}
