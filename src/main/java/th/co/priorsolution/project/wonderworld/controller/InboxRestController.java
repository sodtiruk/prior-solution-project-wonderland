package th.co.priorsolution.project.wonderworld.controller;

import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.InboxModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.service.InboxService;

import java.util.List;

@RestController
@RequestMapping("api")
public class InboxRestController {

    private InboxService inboxService;

    public InboxRestController(InboxService inboxService) {
        this.inboxService = inboxService;
    }

    @GetMapping("/get/inbox/all/user")
    public ResponseModel<List<InboxModel>> getAllInbox(){
        return this.inboxService.getAllInbox();
    }

    @GetMapping("/get/inbox/user/{userId}")
    public ResponseModel<List<InboxModel>>  getInboxUserId(@PathVariable int userId) {
        return this.inboxService.getInboxUserById(userId);
    }

    @GetMapping("/get/inbox/user/unread/{userId}")
    public ResponseModel<List<InboxModel>> getInboxStatusUnreadUserId(@PathVariable int userId) {
        return this.inboxService.getInboxUserStatusUnread(userId);
    }

    @PostMapping("/create/inbox")
    public ResponseModel<Integer> createInboxListUser(@RequestBody List<InboxModel> inboxModels) {
        return this.inboxService.createListInboxByNativeSql(inboxModels);
    }




}
