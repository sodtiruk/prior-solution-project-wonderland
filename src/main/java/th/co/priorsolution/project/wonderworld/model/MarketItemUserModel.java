package th.co.priorsolution.project.wonderworld.model;

import lombok.Data;

@Data
public class MarketItemUserModel {

    private int marketInvId;
    private int invUserId;
    private int invItemId;
    private String itemName;
    private int itemPrice;
    private String marketInvIdStatus;

}
