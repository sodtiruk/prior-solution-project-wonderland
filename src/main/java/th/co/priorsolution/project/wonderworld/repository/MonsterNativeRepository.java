package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MonsterModel;

import java.util.List;

public interface MonsterNativeRepository {

     public List<MonsterModel> findAllMonsters();

     public int insertManyMonsters(List<MonsterModel> monsterModels);



}
