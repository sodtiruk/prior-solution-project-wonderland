package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
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

    public ResponseModel<List<InventoryModel>> getAllInventoryByNativeSql() {
        ResponseModel<List<InventoryModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all inventory");
        try {
            List<InventoryModel> transfromData = this.inventoryNativeRepository.findAllInventory();
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createInventoryByNativeSql(List<InventoryModel> inventoryModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create inventory successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int insertedRow = this.inventoryNativeRepository.insertManyInventory(inventoryModels);
            result.setData(insertedRow);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
