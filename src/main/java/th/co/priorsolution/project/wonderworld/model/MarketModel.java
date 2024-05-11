package th.co.priorsolution.project.wonderworld.model;

import lombok.Data;

@Data
public class MarketModel {

    private int marketInvId;
    private String itemName;
    private int sellByUserId;
    private int itemPrice;
    private String marketInvIdStatus;

}
