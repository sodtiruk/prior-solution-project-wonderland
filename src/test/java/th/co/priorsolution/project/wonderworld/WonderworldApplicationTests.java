package th.co.priorsolution.project.wonderworld;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.MonsterNativeRepository;
import th.co.priorsolution.project.wonderworld.service.MonsterService;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
class WonderworldApplicationTests {

	@Test
	public void test_insertManyMonsters_expect_Response_eq_200(){
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				return monsterModels.size();
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				return 0;
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		List<MonsterModel> monsterModels = new ArrayList<>();
		for (int i=0; i<10; i++){
			MonsterModel x = new MonsterModel();
			x.setMonsterName("test" + i);
			x.setMonsterHealthPoint(10 + i);
			x.setMonsterItemDropId(10 + i);
			monsterModels.add(x);
		}
		ResponseModel<Integer> result = monsterService.createMonstersByNativeSql(monsterModels);
		Assertions.assertTrue(result.getStatusCode() == 200);

	}

	@Test
	public void test_insertManyMonsters_expect_Response_eq_400(){
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				return monsterModels.size();
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				return 0;
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		List<MonsterModel> monsterModels = new ArrayList<>();
		for (int i=0; i<10; i++){
			MonsterModel x = new MonsterModel();
			x.setMonsterName("test" + i);
			monsterModels.add(x);
		}
		ResponseModel<Integer> result = monsterService.createMonstersByNativeSql(monsterModels);
		Assertions.assertTrue(result.getStatusCode() == 400);

	}

	@Test
	public void test_insertManyMonsters_expect_Response_eq_500(){
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				throw new RuntimeException("insert error");
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				return 0;
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		List<MonsterModel> monsterModels = new ArrayList<>();
		for (int i=0; i<10; i++){
			MonsterModel x = new MonsterModel();
			x.setMonsterName("test" + i);
			x.setMonsterHealthPoint(10 + i);
			x.setMonsterItemDropId(10 + i);
			monsterModels.add(x);
		}
		ResponseModel<Integer> result = monsterService.createMonstersByNativeSql(monsterModels);
		Assertions.assertTrue(result.getStatusCode() == 500);

	}

	@Test
	public void test_updateMonster_expectResponse_eq_200(){
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				return 0;
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				int count = 0;
				if (StringUtils.isNotEmpty(monsterModel.getMonsterName())){
					count++;
				}else if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterId()))){
					count++;
				}else if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterHealthPoint()))){
					count++;
				}
				return count;
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterModel monsterModel = new MonsterModel();
		monsterModel.setMonsterId(1);
		monsterModel.setMonsterName("testupdate1");
//		monsterModel.setMonsterHealthPoint(101);
		monsterModel.setMonsterItemDropId(102);

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		ResponseModel<Integer> result = monsterService.updateMonster(monsterModel);
		Assertions.assertTrue(result.getStatusCode() == 200);
	}

	@Test
	public void test_updateMonster_expectResponse_eq_400(){
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				return 0;
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				int count = 0;
				if (StringUtils.isNotEmpty(monsterModel.getMonsterName())){
					count++;
				}else if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterId()))){
					count++;
				}else if (StringUtils.isNotEmpty(String.valueOf(monsterModel.getMonsterHealthPoint()))){
					count++;
				}
				return count;
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterModel monsterModel = new MonsterModel();
		monsterModel.setMonsterName("testupdate1");
		monsterModel.setMonsterHealthPoint(101);
		monsterModel.setMonsterItemDropId(102);

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		ResponseModel<Integer> result = monsterService.updateMonster(monsterModel);
		Assertions.assertTrue(result.getStatusCode() == 400);
	}

	@Test
	public void test_updateMonster_expectResponse_eq_500(){
		//throw error
		MonsterNativeRepository monsterNativeRepository = new MonsterNativeRepository() {
			@Override
			public List<MonsterModel> findAllMonsters() {
				return List.of();
			}

			@Override
			public int insertManyMonsters(List<MonsterModel> monsterModels) {
				return 0;
			}

			@Override
			public int updateMonsterByNativeSql(MonsterModel monsterModel) {
				throw new RuntimeException();
			}

			@Override
			public void deleteUserIdByNativeSql(MonsterModel monsterModel) {

			}
		};

		MonsterModel monsterModel = new MonsterModel();
		monsterModel.setMonsterId(1);
		monsterModel.setMonsterName("testupdate1");
		monsterModel.setMonsterHealthPoint(101);
		monsterModel.setMonsterItemDropId(102);

		MonsterService monsterService = new MonsterService(monsterNativeRepository);
		ResponseModel<Integer> result = monsterService.updateMonster(monsterModel);
		Assertions.assertTrue(result.getStatusCode() == 500);
	}


}
