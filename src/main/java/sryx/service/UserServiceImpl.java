package sryx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sryx.dao.UserMapper;
import sryx.pojo.User;
import sryx.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User getUserById(int id) {
		return userMapper.getUserById(id);
	}

	public void saveUser(User user) {
		userMapper.saveUser(user);
	}

}
