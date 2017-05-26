package sryx.service;

import sryx.pojo.User;

public interface UserService {
	public User getUserById(int id);
	
	public void saveUser(User user);
}
