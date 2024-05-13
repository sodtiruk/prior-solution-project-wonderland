package th.co.priorsolution.project.wonderworld.controller;


import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
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

    @GetMapping("/find/inventory/{userId}")
    public ResponseModel<List<InventoryItemEachUserModel>> createMonsters(@PathVariable int userId){
        return this.inventoryService.findInventoryByIdNativeSql(userId);
    }

}
