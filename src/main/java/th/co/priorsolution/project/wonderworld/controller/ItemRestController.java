package th.co.priorsolution.project.wonderworld.controller;

import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.ItemModel;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("api")
public class ItemRestController {

    private ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/find/all/items")
    public ResponseModel<List<ItemModel>> getAllMonsters(){
        return this.itemService.getAllItemsByNativeSql();
    }

    @PostMapping("/create/items")
    public ResponseModel<Integer> createMonsters(@RequestBody List<ItemModel> itemModels){
        return this.itemService.createItemsByNativeSql(itemModels);
    }

    //update
    @PutMapping("update/item")
    public ResponseModel<Integer> updateItem(@RequestBody ItemModel itemModel){
        return this.itemService.updateMonster(itemModel);
    }

    //delete
    @DeleteMapping("delete/item")
    public ResponseModel<Void> deleteItemById(@RequestBody ItemModel itemModel){
        return this.itemService.deleteMonsterId(itemModel);
    }



}
