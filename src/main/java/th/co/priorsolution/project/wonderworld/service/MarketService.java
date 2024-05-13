package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.model.MarketModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;

import java.util.List;

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


}
