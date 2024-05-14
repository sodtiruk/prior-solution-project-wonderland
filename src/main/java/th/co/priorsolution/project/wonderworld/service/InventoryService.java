package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.InventoryItemEachUserModel;
import th.co.priorsolution.project.wonderworld.model.InventoryModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.InventoryNativeRepository;

import java.util.List;

@Service
public class InventoryService {

    private InventoryNativeRepository inventoryNativeRepository;

    public InventoryService(InventoryNativeRepository inventoryNativeRepository) {
        this.inventoryNativeRepository = inventoryNativeRepository;
    }

    public ResponseModel<List<InventoryItemEachUserModel>> getAllInventoryByNativeSql() {
        ResponseModel<List<InventoryItemEachUserModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all inventory");
        try {
            List<InventoryItemEachUserModel> transfromData = this.inventoryNativeRepository.findAllInventory();
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<List<InventoryItemEachUserModel>> findInventoryByIdNativeSql(int userId) {
        ResponseModel<List<InventoryItemEachUserModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("find inventory user successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            List<InventoryItemEachUserModel> inventoryUserId = this.inventoryNativeRepository.findInventoryUserId(userId);
            result.setData(inventoryUserId);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
