package th.co.priorsolution.project.wonderworld.model;

import lombok.Data;

@Data
public class InventoryItemEachUserModel {

    private int invId;
    private int invUserId;
    private String userName;
    private int userAtk;
    private int userBalance;
    private int invItemId;
    private String itemName;

}
