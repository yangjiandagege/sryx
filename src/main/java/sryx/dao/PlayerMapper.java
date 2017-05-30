package sryx.dao;

import java.util.List;

import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.Role;

public interface PlayerMapper {
	Integer updatePlayer(Player player);
	
	Integer createGame(Game game);
	
	Integer getMaxIdFormGame();
	
	Game getGameById(Integer gameId);
	
	Game getGameByInviteCode(String inviteCode);
	
	Integer addRole(Role role);

	Integer getMaxIdFormRole();

	Integer updateRole(Role role);
	
	List<Role> getUnallocatedRoleListInGame(Integer gameId);

	List<Role> getRoleListInGame(Integer gameId);
	
	//获得我在本局游戏中的角色
	Role getMyRoleInGame(Role role);
	
	//更新游戏状态
	Integer updateGameState(Game game);
	
	//获取游戏状态
	Integer getGameState(Integer gameId);
}
