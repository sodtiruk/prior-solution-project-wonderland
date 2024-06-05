package th.co.priorsolution.project.wonderworld.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.InboxModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.repository.InboxNativeRepository;

import java.util.List;

@Service
public class InboxService {

    private InboxNativeRepository inboxNativeRepository;

    public InboxService(InboxNativeRepository inboxNativeRepository) {
        this.inboxNativeRepository = inboxNativeRepository;
    }

    public ResponseModel<List<InboxModel>> getAllInbox() {
        ResponseModel<List<InboxModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create inbox successfully");
        try {
            List<InboxModel> data = this.inboxNativeRepository.getAllInboxByNativeSql();
            result.setData(data);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createListInboxByNativeSql(List<InboxModel> inboxModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create inbox successfully");
        try {
            int data = this.inboxNativeRepository.createListInboxUser(inboxModels);
            result.setData(1);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<List<InboxModel>> getInboxUserById(int userId) {
        ResponseModel<List<InboxModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("search successfully");
        try {
            List<InboxModel> data = this.inboxNativeRepository.getInboxUserIdByNativeSql(userId);
            result.setData(data);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<List<InboxModel>> getInboxUserStatusUnread(int userId) {
        ResponseModel<List<InboxModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("search successfully");
        try {
            //get data inbox unread
            List<InboxModel> data = this.inboxNativeRepository.getInboxUserStatusUnread(userId);

            // update inbox readed
            this.inboxNativeRepository.updateStatusInboxUser(userId);

            result.setData(data);

        } catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
