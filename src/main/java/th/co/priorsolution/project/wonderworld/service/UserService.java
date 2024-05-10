package th.co.priorsolution.project.wonderworld.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import th.co.priorsolution.project.wonderworld.model.ResponseModel;
import th.co.priorsolution.project.wonderworld.model.UserModel;
import th.co.priorsolution.project.wonderworld.repository.UserNativeRepository;

import java.util.List;

@Service
public class UserService {

    private UserNativeRepository userNativeRepository;

    public UserService(UserNativeRepository userNativeRepository) {
        this.userNativeRepository = userNativeRepository;
    }

    public ResponseModel<List<UserModel>> getAllUsersByNativeSql(){
        ResponseModel<List<UserModel>> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("received all users");
        try {
            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            result.setData(transfromData);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> createUserByNativeSql(List<UserModel> userModels) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(200);
        result.setDescription("create user successfully");
        try {
//            List<UserModel> transfromData = userNativeRepository.findAllUsers();
            int insertedRow = this.userNativeRepository.insertManyUser(userModels);
            result.setData(insertedRow);

        }catch (Exception e) {
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }
}
