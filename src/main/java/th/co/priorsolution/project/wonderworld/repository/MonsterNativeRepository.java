package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MonsterModel;

import java.util.List;

public interface MonsterNativeRepository {

     public List<MonsterModel> findAllMonsters();

     public int insertManyMonsters(List<MonsterModel> monsterModels);

     public int updateMonsterByNativeSql(MonsterModel monsterModel);

     public void deleteUserIdByNativeSql(MonsterModel monsterModel);
}
