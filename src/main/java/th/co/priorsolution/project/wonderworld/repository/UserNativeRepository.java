package th.co.priorsolution.project.wonderworld.repository;


import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;

import java.util.List;
import java.util.Map;

public interface UserNativeRepository {

    public List<UserModel> findAllUsers();

    public int insertManyUser(List<UserModel> userModels);

    public MonsterModel attackMonster(Map<String, Object> data);

    public int updateUserByNativeSql(UserModel userModel);

    public void deleteUserIdByNativeSql(UserModel userModel);
}
