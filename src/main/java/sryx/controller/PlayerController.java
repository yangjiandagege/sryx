package sryx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import sryx.service.PlayerService;
import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.ReturnPojo;
import sryx.pojo.Role;

@Controller
public class PlayerController extends BaseController{
	@Autowired
	private PlayerService playerService;
	
    @RequestMapping("")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/updateplayer")
    public void updatePlayer(Player player, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
        rp = this.playerService.updatePlayer(player);
        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/creategame")
    public void createGame(Game game, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != game){
    		rp = this.playerService.createGame(game);
    	}else{
        	logger.error("参数异常！");
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/getgamebyid")
    public void getGameById(Game game, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != game){
    		rp = this.playerService.getGame(game);
    	}else{
        	logger.error("参数异常！");
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/addroletogame")
    public void addRoleToGame(Role role, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != role){
    		rp = this.playerService.addRoleToGame(role);
    	}else{
        	logger.error("参数异常！");
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/updaterole")
    public void updateRole(Role role, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != role){
    		rp = this.playerService.updateRole(role);
    	}else{
        	logger.error("参数异常！");
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
}