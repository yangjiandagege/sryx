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
    
    @RequestMapping("/updategamestate")
    public void updateGameState(Game game, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != game){
    		rp = this.playerService.updateGameState(game);
    	}else{
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
    		rp = this.playerService.getGameById(game.getGameId());
    	}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/getgamebyinvitecode")
    public void getGameByInviteCode(Game game, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != game){
    		rp = this.playerService.getGameByInviteCode(game.getInviteCode());
    	}else{
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
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    @RequestMapping("/getrolelistingame")
    public void getRoleListInGame(Game game, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != game){
    		rp = this.playerService.getRoleListInGame(game);
    	}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
    
    
    @RequestMapping("/getmyroleingame")
    public void getMyRoleInGame(Role role, HttpServletRequest request, HttpServletResponse response) {
    	ReturnPojo rp = new ReturnPojo();
    	if(null != role){
    		rp = this.playerService.getMyRoleInGame(role);
    	}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("参数异常！");
			rp.setResult("fail");
    	}

        writeJsonToResponse(response, JSONObject.toJSON(rp).toString());
    }
}