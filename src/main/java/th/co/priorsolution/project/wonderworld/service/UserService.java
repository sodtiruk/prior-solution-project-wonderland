package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.UserNativeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserNativeRepository userNativeRepository;

    public UserService(UserNativeRepository userNativeRepository) {
        this.userNativeRepository = userNativeRepository;
    }

    public ResponseModel<List<UserModel>> getAllUsersByNativeSql(){
        ResponseModel<List<UserModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all users");
        try {
            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            result.setData(transfromData);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createUserByNativeSql(List<UserModel> userModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create user successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int insertedRow = this.userNativeRepository.insertManyUser(userModels);
            result.setData(insertedRow);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

//    public ResponseModel<List<UserModel>> getUserIdByNativeSql(int userId) {
//
//    }

    //api attack monster
    // Request body
    // userId
    // monsterId
    public ResponseModel<MonsterModel> userAttackMonsterById(@RequestBody Map<String, Object> data){

        ResponseModel<MonsterModel> result = new ResponseModel<MonsterModel>();
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();

            Object damageUser = userNativeRepository.getDamageUserByNativeSql(data);
            Object healthMonster = userNativeRepository.getHealthMonsterByNativeSql(data);

            Object monsterWasAttackedByDamage = ((int)healthMonster - (int)damageUser);

            userNativeRepository.updateHealthMonster(monsterWasAttackedByDamage, data);

            Object monsterId = data.get("monsterId");
            MonsterModel monsterWasAttack = userNativeRepository.findMonsterById((int)monsterId);

            if ((int)monsterWasAttackedByDamage <= 0) {
                //remove monster
                userNativeRepository.deleteMonsterById(data);

                // and then add item in inventory user
                Object userId = data.get("userId");
                Object itemId = monsterWasAttack.getMonsterItemDropId();

                userNativeRepository.addInventoryUser(userId, itemId);
            }

            result.setData(monsterWasAttack);
            result.setStatusCode(200);
            result.setDescription("attack monster 1 hit");

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;

    }

    public ResponseModel<Integer> updateUser(UserModel userModel) {
        ResponseModel<Integer> result = new ResponseModel<>();

        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int rowUpdated = this.userNativeRepository.updateUserByNativeSql(userModel);
            result.setData(rowUpdated);

            result.setStatusCode(200);
            result.setDescription("update successfully");

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Void> deleteUserId(UserModel userModel) {

        ResponseModel<Void> result = new ResponseModel<>();

        try {
            this.userNativeRepository.deleteUserIdByNativeSql(userModel);

            result.setStatusCode(200);
            result.setDescription("delete successfully");

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;

    }
}
