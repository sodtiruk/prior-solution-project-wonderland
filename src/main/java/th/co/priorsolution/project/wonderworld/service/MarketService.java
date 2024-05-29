package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;

import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    private MarketNativeRepository marketNativeRepository;

    public MarketService(MarketNativeRepository marketNativeRepository) {
        this.marketNativeRepository = marketNativeRepository;
    }

    public ResponseModel<List<MarketItemUserModel>> getAllMarketsByNativeSql() {
        ResponseModel<List<MarketItemUserModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("search all item in market successfully");
        try {
            List<MarketItemUserModel> transfromData = this.marketNativeRepository.findAllItems();
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<MarketItemUserModel> sellItemUserByNativeSql(Map<String, Object> data) {
        ResponseModel<MarketItemUserModel> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("sell item successfully");
        try {
            //do something
            //create item in market
            this.marketNativeRepository.createItemMarket(data);

            //get item
            MarketItemUserModel transfromData = this.marketNativeRepository.getItemUserWasSelledInMarket(data);
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;

    }

    public ResponseModel<String> buyItemByNativeSql(Map<String, Object> data) {
        ResponseModel<String> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("query successfully");
        try {
            String transfromData = this.marketNativeRepository.buyItemInMarket(data);
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
