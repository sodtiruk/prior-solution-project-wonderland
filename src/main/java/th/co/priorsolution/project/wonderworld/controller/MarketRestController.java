package th.co.priorsolution.project.wonderworld.controller;


import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.model.MarketModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.service.MarketService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MarketRestController {

    private MarketService marketService;

    public MarketRestController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/find/all/market")
    public ResponseModel<List<MarketItemUserModel>> getAllMonsters(){
        return this.marketService.getAllMarketsByNativeSql();
    }

    //sell item
    @PostMapping("/sell/item")
    public ResponseModel<MarketItemUserModel> sellItemUser(@RequestBody Map<String, Object> data){
        return this.marketService.sellItemUserByNativeSql(data);
    }

    //buy item
    @PostMapping("/buy/item")
    public ResponseModel<String> buyItemInMarket(@RequestBody Map<String, Object> data){
        return this.marketService.buyItemByNativeSql(data);
    }


}
