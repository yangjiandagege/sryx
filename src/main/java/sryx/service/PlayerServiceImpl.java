package sryx.service;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sryx.dao.PlayerMapper;
import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.ReturnPojo;
import sryx.pojo.Role;
import sryx.utils.TimeUtils;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService{
	@Autowired
	private PlayerMapper playerMapper;
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public ReturnPojo updatePlayer(Player player) {
		ReturnPojo rp = new ReturnPojo();
		if(1 == playerMapper.updatePlayer(player)){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult("success");
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		
		return rp;
	}

	@Override
	public ReturnPojo createGame(Game game) {
		ReturnPojo rp = new ReturnPojo();
		String inviteCode = getFourRandom();
		game.setInviteCode(inviteCode);
		game.setStartTime(TimeUtils.getCurrTime());
		if(1 == playerMapper.createGame(game)){
			Integer gameId = playerMapper.getMaxIdFormGame();
			
			for(int i=0; i < game.getKillerNum(); i++){
				Role killer = new Role();
				killer.setGameId(gameId);
				killer.setRoleType(0);
				killer.setVictory(0);
				playerMapper.addRole(killer);
			}
			
			for(int i=0; i < game.getPoliceNum(); i++){
				Role police = new Role();
				police.setGameId(gameId);
				police.setRoleType(1);
				police.setVictory(0);
				playerMapper.addRole(police);
			}
			
			for(int i=0; i < game.getCitizenNum(); i++){
				Role citizen = new Role();
				citizen.setGameId(gameId);
				citizen.setRoleType(2);
				citizen.setVictory(0);
				playerMapper.addRole(citizen);
			}
			
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(gameId);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		return rp;
	}

	@Override
	public ReturnPojo getGame(Game game) {
		ReturnPojo rp = new ReturnPojo();
		Game returnGm = playerMapper.getGame(game);
		if(null != returnGm){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(returnGm);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		logger.error("------returnGm--."+returnGm);
		return rp;
	}

	@Override
	public ReturnPojo addRoleToGame(Role role) {
		ReturnPojo rp = new ReturnPojo();
		
		if(1 == playerMapper.addRole(role)){
			Integer roleId = playerMapper.getMaxIdFormRole();
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(roleId);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		return rp;
	}

	@Override
	public ReturnPojo updateRole(Role role) {
		ReturnPojo rp = new ReturnPojo();
		
		//查询到所有未被玩家选择的角色列表
		List<Role> roleList= playerMapper.getUnallocatedRoleListInGame(role.getGameId());
		
		if(roleList.size() > 0){
			//随机选中一个角色
			Random rand = new Random();
			int randNum = rand.nextInt(roleList.size());
			Role randRole = roleList.get(randNum);
			
			//为角色写入玩家信息
			randRole.setPlayerId(role.getPlayerId());
			randRole.setPlayerNickName(role.getPlayerNickName());
			randRole.setPlayerAvatarUrl(role.getPlayerAvatarUrl());
			randRole.setVictory(3); //3表示游戏还在进行中
			
			//更新角色信息
			if(1 == playerMapper.updateRole(randRole)){
				Integer roleId = playerMapper.getMaxIdFormRole();
				rp.setReturnCode("200");
				rp.setReturnMsg("操作成功！");
				rp.setResult(roleId);
			}else{
				rp.setReturnCode("201");
				rp.setReturnMsg("操作失败！");
				rp.setResult("fail");
			}
		}else{
			rp.setReturnCode("202");
			rp.setReturnMsg("本局玩家已满，您可以等待下一局！");
			rp.setResult("fail");
		}
		return rp;
	}
	
	public static String getFourRandom(){
		Random random = new Random();
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
		if(randLength<4){
			for(int i=1; i<=4-randLength; i++)
				fourRandom = "0" + fourRandom;
		}
		return fourRandom;
	}


}