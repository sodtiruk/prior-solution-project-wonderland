package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.MonsterNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class MonsterNativeRepositoryImpl implements MonsterNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public MonsterNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MonsterModel> findAllMonsters() {
        String sql = " select monster_id, monster_name, monster_health_point, monster_item_drop_id from monsters ";

        List<MonsterModel> result = this.jdbcTemplate.query(sql, new RowMapper<MonsterModel>() {
            @Override
            public MonsterModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                MonsterModel m = new MonsterModel();
                int col = 1;
                m.setMonsterId(rs.getInt(col++));
                m.setMonsterName(rs.getString(col++));
                m.setMonsterHealthPoint(rs.getInt(col++));
                m.setMonsterItemDropId(rs.getInt(col++));

                return m;
            }
        });
        return result;
    }

    @Override
    public int insertManyMonsters(List<MonsterModel> monsterModels) {

        List<Object> paramList = new ArrayList<>();
        String sql = " insert into monsters (monster_id, monster_name, monster_health_point, monster_item_drop_id) values ";

        StringJoiner stringJoiner = new StringJoiner(",");
        for (MonsterModel m: monsterModels) {
            String value = "((select max(monster_id)+1 from users u), ?, ?, ?)";

            paramList.add(m.getMonsterName());
            paramList.add(m.getMonsterHealthPoint());
            paramList.add(m.getMonsterItemDropId());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }
}
