package th.co.priorsolution.project.wonderworld.controller;

import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("api")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find/all/users")
    public ResponseModel<List<UserModel>> getAllUsers(){
        return this.userService.getAllUsersByNativeSql();
    }

    @PostMapping("/create/users")
    public ResponseModel<Integer> createUser(@RequestBody List<UserModel> userModels){
        return this.userService.createUserByNativeSql(userModels);
    }





}
