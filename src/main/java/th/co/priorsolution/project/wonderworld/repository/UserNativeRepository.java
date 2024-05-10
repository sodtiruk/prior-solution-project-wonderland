package th.co.priorsolution.project.wonderworld.repository;


import org.springframework.stereotype.Repository;
import th.co.priorsolution.project.wonderworld.model.UserModel;

import java.util.List;

public interface UserNativeRepository {

    public List<UserModel> findAllUsers();

    public int insertManyUser(List<UserModel> userModels);
}
