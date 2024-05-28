package th.co.priorsolution.project.wonderworld.repository;


import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;

import java.util.List;
import java.util.Map;

public interface UserNativeRepository {

    public List<UserModel> findAllUsers();

    public int insertManyUser(List<UserModel> userModels);

    public int updateUserByNativeSql(UserModel userModel);

    public void deleteUserIdByNativeSql(UserModel userModel);

    public Object getDamageUserByNativeSql(Map<String, Object> data);

    public Object getHealthMonsterByNativeSql(Map<String, Object> data);

    public void updateHealthMonster(Object mosnterObject, Map<String, Object> data);

    public void deleteMonsterById(Map<String, Object> data);

    public MonsterModel findMonsterById(int monsterId);

    public void addInventoryUser(Object userId, Object itemId);


}
