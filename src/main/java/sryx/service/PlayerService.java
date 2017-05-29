package sryx.service;

import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.ReturnPojo;
import sryx.pojo.Role;

public interface PlayerService {
	ReturnPojo updatePlayer(Player player);
	
	ReturnPojo createGame(Game game);
	
	ReturnPojo getGame(Game game);
	
	ReturnPojo addRoleToGame(Role role);

	ReturnPojo updateRole(Role role);
}
