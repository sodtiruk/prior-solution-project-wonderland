package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import java.util.List;
import java.util.Map;

public interface MarketNativeRepository {

    public List<MarketItemUserModel> findAllItems();

    public MarketItemUserModel getItemUserWasSelledInMarket(Map<String, Object> data);

    public void createItemMarket(Map<String, Object> data);

    public Object getUserBalance(Map<String, Object> data);

    public Object getItemPrice(Map<String, Object> data);

    public void updateMarket(Map<String, Object> data);

    public void changeInventoryUser(Map<String, Object> data);

}
