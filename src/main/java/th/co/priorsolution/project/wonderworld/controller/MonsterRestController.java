package th.co.priorsolution.project.wonderworld.controller;

import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.service.MonsterService;

import java.util.List;

@RestController
@RequestMapping("api")
public class MonsterRestController {

    private MonsterService monsterService;

    public MonsterRestController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping("/find/all/monsters")
    public ResponseModel<List<MonsterModel>> getAllMonsters(){
        return this.monsterService.getAllMonstersByNativeSql();
    }

    @PostMapping("/create/monsters")
    public ResponseModel<Integer> createMonsters(@RequestBody List<MonsterModel> monsterModels){
        return this.monsterService.createMonstersByNativeSql(monsterModels);
    }

}
