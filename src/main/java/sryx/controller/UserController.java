package sryx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sryx.service.UserService;
import sryx.pojo.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
    @RequestMapping("")
    public String Create(Model model) {
        return "create";
    }

    @RequestMapping("/save")
    public String Save(@ModelAttribute("form") User user, Model model) { // user:视图层传给控制层的表单对象；model：控制层返回给视图层的对象
        this.userService.saveUser(user);
    	model.addAttribute("user", user);
        return "detail";
    }
//    
//	public UserService getUserService() {
//		return userService;
//	}
//
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
//
//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody
//	String showUser() {
//		return "12";
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public @ResponseBody
//	User showOneUser(@PathVariable("id") int id) {
//		return userService.getUserById(id);
//	}
}