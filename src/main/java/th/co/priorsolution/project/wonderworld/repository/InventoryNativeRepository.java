package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.InventoryModel;
import th.co.priorsolution.project.wonderworld.model.ItemModel;

import java.util.List;

public interface InventoryNativeRepository {


    public List<InventoryModel> findAllInventory();

    public int insertManyInventory(List<InventoryModel> inventoryModels);

}