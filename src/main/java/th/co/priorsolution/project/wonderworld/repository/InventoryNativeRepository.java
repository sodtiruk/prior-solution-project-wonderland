package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
import th.co.priorsolution.project.wonderworld.model.InventoryModel;

import java.util.List;

public interface InventoryNativeRepository {


    public List<InventoryItemEachUserModel> findAllInventory();

    public List<InventoryItemEachUserModel> findInventoryUserId(int userId);

    public void deleteInventoryIdByNativeSql(InventoryModel inventoryModel);
}
