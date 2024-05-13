package th.co.priorsolution.project.wonderworld.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.project.wonderworld.model.MonsterModel;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.service.UserService;

import java.util.List;
import java.util.Map;

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

//    @GetMapping("find/user/{userId}")
//    public ResponseModel<List<UserModel>> getUserById(@PathVariable("userId") int userId){
//        return this.userService.getUserIdByNativeSql(userId);
//    }

    //attack monster
    @PostMapping("attack/monster")
    public ResponseModel<MonsterModel> attackMonsterById(@RequestBody Map<String, Object> data){
        return this.userService.userAttackMonsterById(data);
    }







}
