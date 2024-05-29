package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.MarketNativeRepository;
import th.co.priorsolution.project.wonderworld.repository.UserNativeRepository;

import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    private MarketNativeRepository marketNativeRepository;
    private UserNativeRepository userNativeRepository;

    public MarketService(MarketNativeRepository marketNativeRepository, UserNativeRepository userNativeRepository) {
        this.marketNativeRepository = marketNativeRepository;
        this.userNativeRepository = userNativeRepository;
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
            // do something
            Object userBalance = this.marketNativeRepository.getUserBalance(data);
            Object itemPrice = this.marketNativeRepository.getItemPrice(data);

            if ((int)userBalance >= (int)itemPrice) {
                // update money user
                int balanceLeft = (int)userBalance - (int)itemPrice;
                UserModel userModel = new UserModel();
                userModel.setUserBalance(balanceLeft);
                this.userNativeRepository.updateUserByNativeSql(userModel);

                //update market
                this.marketNativeRepository.updateMarket(data);

                //change inventory
                this.marketNativeRepository.changeInventoryUser(data);

                result.setData("You buy successfully");
            }else {
                result.setData("You can buy because money not enough");
            }
            return result;

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
