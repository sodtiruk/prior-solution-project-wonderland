package th.co.priorsolution.project.wonderworld.controller;


import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.InventoryModel;
import th.co.priorsolution.project.wonderworld.model.ItemModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("api")
public class InventoryRestController {

    private InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/find/all/inventory")
    public ResponseModel<List<InventoryModel>> getAllMonsters(){
        return this.inventoryService.getAllInventoryByNativeSql();
    }

    @PostMapping("/create/inventory")
    public ResponseModel<Integer> createMonsters(@RequestBody List<InventoryModel> inventoryModels){
        return this.inventoryService.createInventoryByNativeSql(inventoryModels);
    }

}
