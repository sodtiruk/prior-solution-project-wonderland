package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.MonsterNativeRepository;

import java.util.List;

@Service
public class MonsterService {

    private MonsterNativeRepository monsterNativeRepository;

    public MonsterService(MonsterNativeRepository monsterNativeRepository) {
        this.monsterNativeRepository = monsterNativeRepository;
    }

    public ResponseModel<List<MonsterModel>> getAllMonstersByNativeSql(){
        ResponseModel<List<MonsterModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all monster");
        try {
            List<MonsterModel> transfromData = this.monsterNativeRepository.findAllMonsters();
            result.setData(transfromData);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createMonstersByNativeSql(List<MonsterModel> monsterModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create monsters successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int insertedRow = this.monsterNativeRepository.insertManyMonsters(monsterModels);
            result.setData(insertedRow);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
