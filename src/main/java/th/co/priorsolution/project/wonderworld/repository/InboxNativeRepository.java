package th.co.priorsolution.project.wonderworld.repository;

import th.co.priorsolution.project.wonderworld.model.InboxModel;

import java.util.List;

public interface InboxNativeRepository{

    public int createListInboxUser(List<InboxModel> inboxModels);

    public List<InboxModel> getAllInboxByNativeSql();

    public List<InboxModel> getInboxUserIdByNativeSql(int userId);

    public List<InboxModel> getInboxUserStatusUnread(int userId);

    public void updateStatusInboxUser(int userId);



}
