package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MarketItemUserModel;
import java.util.List;

public interface MarketNativeRepository {

    public List<MarketItemUserModel> findAllItems();

}
