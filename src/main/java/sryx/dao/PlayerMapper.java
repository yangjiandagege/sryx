package sryx.dao;

import java.util.List;

import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.Role;

public interface PlayerMapper {
	Integer updatePlayer(Player player);
	
	Integer createGame(Game game);
	
	Integer getMaxIdFormGame();
	
	Game getGame(Game game);
	
	Integer addRole(Role role);

	Integer getMaxIdFormRole();

	Integer updateRole(Role role);
	
	List<Role> getUnallocatedRoleListInGame(Integer gameId);
}
