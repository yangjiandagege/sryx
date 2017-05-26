package sryx.dao;

import sryx.pojo.User;

public interface UserMapper {
    User getUserById(Integer id);

    void saveUser(User record);
}