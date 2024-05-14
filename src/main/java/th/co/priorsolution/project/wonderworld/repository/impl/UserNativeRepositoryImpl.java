package th.co.priorsolution.project.wonderworld.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.UserNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserNativeRepositoryImpl implements UserNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public UserNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<UserModel> findAllUsers() {
        String sql = " select user_id, user_name, user_atk, user_balance from users ";

        List<UserModel> result = this.jdbcTemplate.query(sql, new RowMapper<UserModel>() {
            @Override
            public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserModel x = new UserModel();
                int col = 1;
                x.setUserId(rs.getInt(col++));
                x.setUserName(rs.getString(col++));
                x.setUserAtk(rs.getInt(col++));
                x.setUserBalance(rs.getInt(col++));

                return x;
            }
        });

        return result;
    }

    @Override
    public int insertManyUser(List<UserModel> userModels) {

        List<Object> paramList = new ArrayList<>();
        String sql = " insert into users (user_id, user_name, user_atk, user_balance) values ";

        StringJoiner stringJoiner = new StringJoiner(",");
        for (UserModel u: userModels) {
            String value = "((select max(user_id)+1 from users u), ?, ?, ?)";

            paramList.add(u.getUserName());
            paramList.add(u.getUserAtk());
            paramList.add(u.getUserBalance());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }

    @Override
    public MonsterModel attackMonster(Map<String, Object> data) {
        List<Object> paramList = new ArrayList<>();

        String sqlGetDamageAttackUser = "select user_atk from users where user_id = ?";
        String sqlGetHealthMonster = "select monster_health_point from monsters where monster_id = ?";

        String sqlUpdateThenAttacked = " update monsters set monster_health_point = ? where monster_id = ?";

        Object userId = data.get("userId");
        Object monsterId = data.get("monsterId");
        //get damage attack user
        Object damageAtk = this.jdbcTemplate.queryForObject(sqlGetDamageAttackUser, new Object[]{userId}, Integer.class);
        //get monsterHealth
        Object monsterHealth = this.jdbcTemplate.queryForObject(sqlGetHealthMonster, new Object[]{monsterId}, Integer.class);

        Object monsterWasAttackedByDamage = ((int)monsterHealth - (int)damageAtk);
        paramList.add(monsterWasAttackedByDamage);
        paramList.add(monsterId);

        this.jdbcTemplate.update(sqlUpdateThenAttacked, paramList.toArray());

        MonsterModel monsterWasAttack = findMonsterById((int)monsterId);

        if ((int)monsterWasAttackedByDamage <= 0){
            //remove monster
            String deleteMonsterWhichDiedSql = " delete from monsters where monster_id = ? ";
            this.jdbcTemplate.update(deleteMonsterWhichDiedSql, (int)monsterId);

            // and then add item in inventory user
            List<Object> paramListForInventory = new ArrayList<>();

            String addItemToInventoryUserSql = " insert into inventory (inv_id, inv_user_id, inv_item_id) values ";
            addItemToInventoryUserSql +=       " ( (select ifnull(max(inv_id)+1, 1) from inventory inv), ?, ? ) ";

            Object getItemMonsterDropId = monsterWasAttack.getMonsterItemDropId();
            paramListForInventory.add(userId);
            paramListForInventory.add(getItemMonsterDropId);

            this.jdbcTemplate.update(addItemToInventoryUserSql, paramListForInventory.toArray());

        }
        return monsterWasAttack;
    }

    @Override
    public int updateUserByNativeSql(UserModel userModel) {
        List<Object> paramList = new ArrayList<>();

        StringJoiner columnSql = new StringJoiner(",");
        String sql = " update users ";
        sql +=       " set ";

        if (StringUtils.isNotEmpty(userModel.getUserName()) && userModel.getUserName() != "0"){
            columnSql.add(" user_name = ? ");
            paramList.add(userModel.getUserName());
        }
        if (StringUtils.isNotEmpty(String.valueOf(userModel.getUserAtk())) && userModel.getUserAtk() != 0){
            columnSql.add(" user_atk = ? ");
            paramList.add(userModel.getUserAtk());
        }
        if (StringUtils.isNotEmpty(String.valueOf(userModel.getUserBalance())) && userModel.getUserBalance() != 0){
            columnSql.add(" user_balance = ? ");
            paramList.add(userModel.getUserBalance());
        }
        sql += columnSql.toString();
        sql += " where user_id = ? ";
        paramList.add(userModel.getUserId());

        int numberColumnUpdated = this.jdbcTemplate.update(sql, paramList.toArray());
        return numberColumnUpdated;
    }

    @Override
    public void deleteUserIdByNativeSql(UserModel userModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " delete from users where user_id = ? ";
        if (StringUtils.isNotEmpty(String.valueOf(userModel.getUserId()))){
            paramList.add(userModel.getUserId());
            this.jdbcTemplate.update(sql, paramList.toArray());
        }
    }

    public MonsterModel findMonsterById(int monsterId) {
        String sql = "SELECT monster_id, monster_name, monster_health_point, monster_item_drop_id FROM monsters WHERE monster_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{monsterId}, (rs, rowNum) -> {

            MonsterModel monster = new MonsterModel();
            monster.setMonsterId(rs.getInt("monster_id"));
            monster.setMonsterName(rs.getString("monster_name"));
            monster.setMonsterHealthPoint(rs.getInt("monster_health_point"));
            monster.setMonsterItemDropId(rs.getInt("monster_item_drop_id"));

            return monster;
        });
    }


}
