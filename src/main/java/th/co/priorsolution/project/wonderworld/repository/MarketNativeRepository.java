package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import java.util.List;
import java.util.Map;

public interface MarketNativeRepository {

    public List<MarketItemUserModel> findAllItems();

    public MarketItemUserModel sellItemUser(Map<String, Object> data);

    public String buyItemInMarket(Map<String, Object> data);
}
