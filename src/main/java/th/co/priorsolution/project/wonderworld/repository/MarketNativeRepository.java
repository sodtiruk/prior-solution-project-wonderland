package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.MarketModel;
import java.util.List;

public interface MarketNativeRepository {

    public List<MarketModel> findAllItems();

}
