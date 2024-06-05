package th.co.priorsolution.project.wonderworld.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.InboxModel;
import th.co.priorsolution.project.wonderworld.repository.InboxNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class InboxNativeRepositoryImpl implements InboxNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public InboxNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InboxModel> getAllInboxByNativeSql() {
        String sql = " select inbox_id, message, user_id, from_user_id, status from inbox";

        List<InboxModel> result = this.jdbcTemplate.query(sql, new RowMapper<InboxModel>() {
            @Override
            public InboxModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                InboxModel x = new InboxModel();
                int col = 1;
                x.setInboxId(rs.getInt(col++));
                x.setMessage(rs.getString(col++));
                x.setUserId(rs.getInt(col++));
                x.setFromUserId(rs.getInt(col++));
                x.setStatus(rs.getString(col++));

                return x;
            }
        });
        return result;
    }

    @Override
    public List<InboxModel> getInboxUserIdByNativeSql(int userId) {
        String sql = " select inbox_id, message, user_id, from_user_id, status from inbox where user_id = ? ";
        List<Object> paremList = new ArrayList<>();
        paremList.add(userId);

        List<InboxModel> result = this.jdbcTemplate.query(sql, new RowMapper<InboxModel>() {
            @Override
            public InboxModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                InboxModel x = new InboxModel();
                int col = 1;
                x.setInboxId(rs.getInt(col++));
                x.setMessage(rs.getString(col++));
                x.setUserId(rs.getInt(col++));
                x.setFromUserId(rs.getInt(col++));
                x.setStatus(rs.getString(col++));

                return x;
            }
        }, paremList.toArray());
        return result;
    }

    @Override
    public List<InboxModel> getInboxUserStatusUnread(int userId) {
        String sql = " select inbox_id, message, user_id, from_user_id, status from inbox where status = 'unread' and user_id = ? ";
        List<Object> paremList = new ArrayList<>();
        paremList.add(userId);

        List<InboxModel> result = this.jdbcTemplate.query(sql, new RowMapper<InboxModel>() {
            @Override
            public InboxModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                InboxModel x = new InboxModel();
                int col = 1;
                x.setInboxId(rs.getInt(col++));
                x.setMessage(rs.getString(col++));
                x.setUserId(rs.getInt(col++));
                x.setFromUserId(rs.getInt(col++));
                x.setStatus(rs.getString(col++));

                return x;
            }
        }, paremList.toArray());
        return result;
    }

    @Override
    public void updateStatusInboxUser(int userId) {
        List<Object> paramList = new ArrayList<>();
        paramList.add(userId);

        String sql = " update inbox ";
        sql +=       " set status = 'readed' ";
        sql +=       " where user_id = ? and status = 'unread' ";

        this.jdbcTemplate.update(sql, paramList.toArray());

    }

    @Override
    public int createListInboxUser(List<InboxModel> inboxModels) {
        List<Object> paramList = new ArrayList<>();
        String sql = " insert into inbox (inbox_id, message, user_id, from_user_id, status) values ";

        StringJoiner stringJoiner = new StringJoiner(",");
        for (InboxModel i: inboxModels) {
            String value = "( (select ifnull(max(inbox_id)+1, 1) from inbox i), ?, ?, ?, ?)";

            paramList.add(i.getMessage());
            paramList.add(i.getUserId());
            paramList.add(i.getFromUserId());
            paramList.add(i.getStatus());

            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();

        int insertedRow = this.jdbcTemplate.update(sql, paramList.toArray());
        return insertedRow;
    }

}
