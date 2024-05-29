package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import java.util.List;
import java.util.Map;

public interface MarketNativeRepository {

    public List<MarketItemUserModel> findAllItems();

    public MarketItemUserModel getItemUserWasSelledInMarket(Map<String, Object> data);

    public String buyItemInMarket(Map<String, Object> data);

    public void createItemMarket(Map<String, Object> data);


}
