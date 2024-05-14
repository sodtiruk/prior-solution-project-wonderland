package th.co.priorsolution.project.wonderworld.repository.impl;

import org.apache.commons.lang3.StringUtils;
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
            String value = "((select ifnull(max(monster_id)+1, 1) from monsters m), ?, ?, ?)";

            paramList.add(m.getMonsterName());
            paramList.add(m.getMonsterHealthPoint());
            paramList.add(m.getMonsterItemDropId());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }

    @Override
    public int updateMonsterByNativeSql(MonsterModel monsterModel) {
        List<Object> paramList = new ArrayList<>();

        StringJoiner columnSql = new StringJoiner(",");
        String sql = " update monsters ";
        sql +=       " set ";

        if (StringUtils.isNotEmpty(monsterModel.getMonsterName()) && monsterModel.getMonsterName() != "0"){
            columnSql.add(" monster_name = ? ");
            paramList.add(monsterModel.getMonsterName());
        }
        if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterHealthPoint())) && monsterModel.getMonsterHealthPoint() != 0){
            columnSql.add(" monster_health_point = ? ");
            paramList.add(monsterModel.getMonsterHealthPoint());
        }
        if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterItemDropId())) && monsterModel.getMonsterItemDropId() != 0){
            columnSql.add(" monster_item_drop_id = ? ");
            paramList.add(monsterModel.getMonsterItemDropId());
        }

        sql += columnSql.toString();
        sql += " where monster_id = ? ";
        paramList.add(monsterModel.getMonsterId());

        int numberColumnUpdated = this.jdbcTemplate.update(sql, paramList.toArray());
        return numberColumnUpdated;
    }

    @Override
    public void deleteUserIdByNativeSql(MonsterModel monsterModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " delete from monsters where monster_id = ? ";
        if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterId()))){
            paramList.add(monsterModel.getMonsterId());
            this.jdbcTemplate.update(sql, paramList.toArray());
        }
    }
}
