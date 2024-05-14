package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.ItemModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.ItemNativeRepository;

import java.util.List;

@Service
public class ItemService {

    private ItemNativeRepository itemNativeRepository;

    public ItemService(ItemNativeRepository itemNativeRepository) {
        this.itemNativeRepository = itemNativeRepository;
    }

    public ResponseModel<List<ItemModel>> getAllItemsByNativeSql() {
        ResponseModel<List<ItemModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all items");
        try {
            List<ItemModel> transfromData = this.itemNativeRepository.findAllItems();
            result.setData(transfromData);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createItemsByNativeSql(List<ItemModel> itemModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create items successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int insertedRow = this.itemNativeRepository.insertManyItems(itemModels);
            result.setData(insertedRow);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> updateMonster(ItemModel itemModel) {
        ResponseModel<Integer> result = new ResponseModel<>();
        try {
            int rowUpdated = this.itemNativeRepository.updateItemByNativeSql(itemModel);
            result.setData(rowUpdated);

            result.setStatusCode(200);
            result.setDescription("update successfully");

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Void> deleteMonsterId(ItemModel itemModel) {
        ResponseModel<Void> result = new ResponseModel<>();
        try {
            this.itemNativeRepository.deleteItemIdByNativeSql(itemModel);

            result.setStatusCode(200);
            result.setDescription("delete successfully");

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }
}
