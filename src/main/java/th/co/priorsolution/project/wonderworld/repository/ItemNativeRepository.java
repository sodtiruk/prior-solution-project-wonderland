package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.ItemModel;

import java.util.List;

public interface ItemNativeRepository {

    public List<ItemModel> findAllItems();

    public int insertManyItems(List<ItemModel> itemModels);

    public int updateItemByNativeSql(ItemModel itemModel);

    public void deleteItemIdByNativeSql(ItemModel itemModel);
}
