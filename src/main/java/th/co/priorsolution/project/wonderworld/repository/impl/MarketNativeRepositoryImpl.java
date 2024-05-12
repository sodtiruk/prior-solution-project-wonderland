package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import th.co.priorsolution.project.wonderworld.model.MarketModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MarketNativeRepositoryImpl implements MarketNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public MarketNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MarketModel> findAllItems() {
        String sql = " select market_inv_id, item_price, market_inv_id_status from market";

        List<MarketModel> result = this.jdbcTemplate.query(sql, new RowMapper<MarketModel>() {
            @Override
            public MarketModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                MarketModel i = new MarketModel();
                int col = 1;
                i.setMarketInvId(rs.getInt(col++));
                i.setItemPrice(rs.getInt(col++));
                i.setMarketInvIdStatus(rs.getString(col++));

                return i;
            }
        });
        return result;

    }
}
