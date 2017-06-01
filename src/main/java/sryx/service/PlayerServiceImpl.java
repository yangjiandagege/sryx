package sryx.service;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
		player.setCreateTime(TimeUtils.getCurrTime());
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
	public ReturnPojo getPlayerById(String playerId) {
		ReturnPojo rp = new ReturnPojo();
		Player returnPly = playerMapper.getPlayerById(playerId);
		if(null != returnPly){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(returnPly);
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
		Integer gameId = null;
		String inviteCode = getFourRandom();
		game.setInviteCode(inviteCode);
		game.setState(0);//状态0：游戏准备中；1：玩家到齐，游戏进行中；2：玩家准备超时，解散；3：房主（法官）主动取消游戏，解散；4：游戏超时，系统强制解散；5：游戏正常结束
		game.setStartTime(TimeUtils.getCurrTime());
		game.setCreateTime(TimeUtils.getCurrTime());
		if(1 == playerMapper.createGame(game)){
			//获取最新gameId
			gameId = playerMapper.getMaxIdFormGame();
			
			//更新player中lastGameId字段
			Player paraPlayer = new Player();
			paraPlayer.setPlayerId(game.getGameOwnerId());
			paraPlayer.setLastGameId(gameId);
			playerMapper.updatePlayerLastGameId(paraPlayer);
			
			//创建一个法官
			Role  judge = new Role();
			judge.setGameId(gameId);
			judge.setRoleType(3);
			judge.setPlayerId(game.getGameOwnerId());
			judge.setPlayerAvatarUrl(game.getGameOwnerAvatarUrl());
			judge.setPlayerNickName(game.getGameOwnerNickName());
			judge.setCreateTime(TimeUtils.getCurrTime());
			playerMapper.addRole(judge);
			
			//创建指定个数的杀手
			for(int i=0; i < game.getKillerNum(); i++){
				Role killer = new Role();
				killer.setGameId(gameId);
				killer.setRoleType(0);
				killer.setCreateTime(TimeUtils.getCurrTime());
				playerMapper.addRole(killer);
			}
			
			//创建指定个数的警察
			for(int i=0; i < game.getPoliceNum(); i++){
				Role police = new Role();
				police.setGameId(gameId);
				police.setRoleType(1);
				police.setCreateTime(TimeUtils.getCurrTime());
				playerMapper.addRole(police);
			}
			
			//创建指定个数的平民
			for(int i=0; i < game.getCitizenNum(); i++){
				Role citizen = new Role();
				citizen.setGameId(gameId);
				citizen.setRoleType(2);
				citizen.setCreateTime(TimeUtils.getCurrTime());
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
		
		if(null != gameId){
			logger.info("Timer For SwitchGameStateTask Start!");
			//开定时器，120s后，如果游戏没有开始则超时解散 0--->2
			Timer timer = new Timer();
			timer.schedule(new SwitchGameStateTask(gameId, 0, 2),120*1000);
		}
		return rp;
	}
	
	class SwitchGameStateTask extends TimerTask{
		private Integer gameId;
		private Integer srcState;
		private Integer desState;
		public SwitchGameStateTask(Integer gameId, Integer srcState, Integer desState) {
			this.gameId = gameId;
			this.srcState = srcState;
			this.desState = desState;
			logger.info("SwitchGameStateTask Paras gameId:"+this.gameId+" srcState:"+this.srcState+" desState:"+this.desState);
		}
		@Override
		public void run(){
			Integer gameState = playerMapper.getGameState(gameId);
			logger.info("Exec SwitchGameStateTask gameId="+gameId+">>>>>"+srcState+"----"+gameState);
			if(srcState == gameState){
				Game paraGm = new Game();
				paraGm.setGameId(gameId);
				paraGm.setState(desState);
				if(1 == playerMapper.updateGameState(paraGm)){
		        	logger.info("Exec SwitchGameStateTask success :"+srcState+"---->"+desState);
				}else{
		        	logger.info("Exec SwitchGameStateTask fail :"+srcState+"---->"+desState);
				}

			}
		}
	}
	
	//状态0：游戏准备中；1：玩家到齐，游戏进行中；2：玩家准备超时，解散；3：房主（法官）主动取消游戏，解散；4：游戏超时，系统强制解散；5：游戏正常结束
	@Override
	public ReturnPojo updateGameState(Game game) {
		ReturnPojo rp = new ReturnPojo();
		if(1 == playerMapper.updateGameState(game)){
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
	public ReturnPojo getGameById(Integer gameId) {
		ReturnPojo rp = new ReturnPojo();
		Game returnGm = playerMapper.getGameById(gameId);
		if(null != returnGm){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(returnGm);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		return rp;
	}


	@Override
	public ReturnPojo getGameByInviteCode(String inviteCode) {
		ReturnPojo rp = new ReturnPojo();
		Game returnGm = playerMapper.getGameByInviteCode(inviteCode);
		if(null != returnGm){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(returnGm);
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
		
		//检查游戏状态，准备态才可以加入游戏
		Integer gameState = playerMapper.getGameState(role.getGameId());
		if(gameState != 0){
			switch(gameState){
			case 1:
				rp.setReturnMsg("游戏已经开始了！");
				break;
			case 2:
			case 3:
				rp.setReturnMsg("游戏已经解散！");
				break;
			case 4:
				rp.setReturnMsg("游戏已经结束！");
				break;
			}
			rp.setReturnCode("204");
			rp.setResult("fail");
			return rp;
		}
		
		//检查是否已经加入过本局游戏
		if(null == playerMapper.getMyRoleInGame(role)){
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
				randRole.setDeath(0); //0:活着   1:被杀手杀死   2：被投票出局
				
				//更新角色信息
				if(1 == playerMapper.updateRole(randRole)){
					Integer roleId = playerMapper.getMaxIdFormRole();
					rp.setReturnCode("200");
					rp.setReturnMsg("操作成功！");
					rp.setResult(roleId);
					
			    	//判断人员是否到齐
					if(roleList.size() == 1){
						logger.info("Timer For SwitchGameStateTask Start!");
						
						Timer timer = new Timer();
						//游戏开始，立即执行
						timer.schedule(new SwitchGameStateTask(role.getGameId(), 0, 1), 10);
						//开定时器，如果到齐本局开始计时，超时(1个小时)未结束的比赛，系统将强制结束  1--->4
						timer.schedule(new SwitchGameStateTask(role.getGameId(), 1, 4),3600*1000);
					}
				}else{
					rp.setReturnCode("201");
					rp.setReturnMsg("操作失败！");
					rp.setResult("fail");
				}
				
				//更新player信息，最近一次的gameId
				Player paraPlayer = new Player();
				paraPlayer.setPlayerId(role.getPlayerId());
				paraPlayer.setLastGameId(role.getGameId());
				playerMapper.updatePlayerLastGameId(paraPlayer);
			}else{
				rp.setReturnCode("202");
				rp.setReturnMsg("本局玩家已满，您可以等待下一局！");
				rp.setResult("fail");
			}
		}else{
			rp.setReturnCode("203");
			rp.setReturnMsg("您已经在本局比赛中了！");
			rp.setResult("fail");
		}
		
		return rp;
	}
	

	@Override
	public ReturnPojo getRoleListInGame(Game game) {
		ReturnPojo rp = new ReturnPojo();
		List<Role> roleList= playerMapper.getRoleListInGame(game.getGameId());
		if(null != roleList){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(roleList);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		return rp;
	}

	@Override
	public ReturnPojo getMyRoleInGame(Role role) {
		ReturnPojo rp = new ReturnPojo();
		Role roleRp= playerMapper.getMyRoleInGame(role);
		if(null != roleRp){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult(roleRp);
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
		}
		
		return rp;
	}

	@Override
	public ReturnPojo updateRoleDeathState(Role role) {
		ReturnPojo rp = new ReturnPojo();
		
		//更新角色存活状态，death 0:活着  1:被杀手杀手  2:被公决出局
		if(1 == playerMapper.updateRoleDeathState(role)){
			rp.setReturnCode("200");
			rp.setReturnMsg("操作成功！");
			rp.setResult("success");
		}else{
			rp.setReturnCode("201");
			rp.setReturnMsg("操作失败！");
			rp.setResult("fail");
			return rp;
		}
		
		//判断游戏是否结束及胜负情况
		Game paraGm = new Game();
		
		Role paraRl = new Role();
		paraRl.setGameId(role.getGameId());
		logger.error("------role.getGameId()----------"+role.getGameId());
		
		// 杀手状态
		paraRl.setRoleType(0);
		logger.error("------getAliveRoleInGameByRoleType start----------");
		if(0 == playerMapper.getAliveRoleInGameByRoleType(paraRl).size()){
			logger.error("getAliveRoleInGameByRoleType killer 0");
			//游戏结束，更新game表和role表，写入胜负结果，写入结束时间
			paraGm.setGameId(role.getGameId());
			paraGm.setState(5);  //更改游戏状态
			paraGm.setResult(0); //0表示本局游戏警察及平民方获得胜利 1杀手获得胜利 2平局
			paraGm.setEndTime(TimeUtils.getCurrTime());
			playerMapper.updateGameResult(paraGm);
			
			paraRl.setVictorySide(0); //胜利方为警察
			playerMapper.updateRoleListVictorySide(paraRl);
			
			rp.setReturnCode("100");
			rp.setReturnMsg("杀手全部死亡，警察及平民方获得胜利！");
			rp.setResult(0);
			return rp;
		}
		// 警察状态
		paraRl.setRoleType(1);
		if(0 == playerMapper.getAliveRoleInGameByRoleType(paraRl).size()){
			logger.error("getAliveRoleInGameByRoleType plice 0");
			//游戏结束，更新game表和role表，写入胜负结果，写入结束时间
			paraGm.setGameId(role.getGameId());
			paraGm.setState(5);  //更改游戏状态
			paraGm.setResult(1); //0表示本局游戏警察及平民方获得胜利 1杀手获得胜利 2平局
			paraGm.setEndTime(TimeUtils.getCurrTime());
			playerMapper.updateGameResult(paraGm);
			
			//杀手胜利
			paraRl.setVictorySide(1); //胜利方为杀手
			playerMapper.updateRoleListVictorySide(paraRl);
			
			rp.setReturnCode("100");
			rp.setReturnMsg("警察全部死亡，杀手方获得胜利！");
			rp.setResult(1);
			return rp;
		}
		// 平民状态
		paraRl.setRoleType(2);
		if(0 == playerMapper.getAliveRoleInGameByRoleType(paraRl).size()){
			logger.error("getAliveRoleInGameByRoleType citizen 0");
			//游戏结束，更新game表和role表，写入胜负结果，写入结束时间
			paraGm.setGameId(role.getGameId());
			paraGm.setState(5);  //更改游戏状态
			paraGm.setResult(2); //0表示本局游戏警察及平民方获得胜利 1杀手获得胜利 2平局
			paraGm.setEndTime(TimeUtils.getCurrTime());
			playerMapper.updateGameResult(paraGm);
			
			//杀手平局
			paraRl.setVictorySide(2);    //平局
			playerMapper.updateRoleListVictorySide(paraRl);
			
			rp.setReturnCode("100");
			rp.setReturnMsg("平民全部死亡，双方平局！");
			rp.setResult(2);
			return rp;
		}
		logger.error("------getAliveRoleInGameByRoleType end----------");
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
