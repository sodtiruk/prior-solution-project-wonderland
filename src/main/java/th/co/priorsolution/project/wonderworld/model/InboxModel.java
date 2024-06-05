package th.co.priorsolution.project.wonderworld.model;

import lombok.Data;

@Data
public class InboxModel {

    private int inboxId;
    private String message;
    private int userId;
    private int fromUserId;
    private String status;

}
