package th.co.priorsolution.project.wonderworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import th.co.priorsolution.project.wonderworld.kafka.component.KafkaCompenent;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.UserNativeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private UserNativeRepository userNativeRepository;
    private KafkaCompenent kafkaCompenent;

    @Value("${kafka.topics.regist}")
    private String registTopic;

    public UserService(UserNativeRepository userNativeRepository, KafkaCompenent kafkaCompenent) {
        this.userNativeRepository = userNativeRepository;
        this.kafkaCompenent = kafkaCompenent;
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

    //this use kafka
    public String objectToJsonString(Object model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(model);
    }

    public ResponseModel<Integer> createUserByNativeSql(List<UserModel> userModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create user successfully");
        try {

//            int insertedRow = this.userNativeRepository.insertManyUser(userModels);

            //kafka
            String message = this.objectToJsonString(userModels);
            this.kafkaCompenent.sendData(message, registTopic);

//            result.setData(insertedRow);

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

            this.userNativeRepository.updateHealthMonster(monsterWasAttackedByDamage, data);

            Object monsterId = data.get("monsterId");
            MonsterModel monsterWasAttack = userNativeRepository.findMonsterById((int)monsterId);

            if ((int)monsterWasAttackedByDamage <= 0) {
                //remove monster
                this.userNativeRepository.deleteMonsterById(data);

                //add money user
                Object money = this.userNativeRepository.getBalanceUserByNativeSql(data);
                UserModel x = new UserModel();
                // random money 20 to 100
                Random random = new Random();
                int randomInt = 20 + random.nextInt(81); // (0 to 80) + 20
                x.setUserBalance((int)money + randomInt);

                this.userNativeRepository.updateUserByNativeSql(x);

                // and then add item in inventory user
                Object userId = data.get("userId");
                Object itemId = monsterWasAttack.getMonsterItemDropId();

                this.userNativeRepository.addInventoryUser(userId, itemId);
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
