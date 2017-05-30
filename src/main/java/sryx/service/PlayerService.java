package sryx.service;

import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.ReturnPojo;
import sryx.pojo.Role;

public interface PlayerService {
	ReturnPojo updatePlayer(Player player);
	
	ReturnPojo createGame(Game game);
	
	ReturnPojo getGameById(Integer gameId);
	
	ReturnPojo getGameByInviteCode(String inviteCode);
	
	ReturnPojo addRoleToGame(Role role);

	ReturnPojo updateRole(Role role);

	ReturnPojo getRoleListInGame(Game game);

	ReturnPojo getMyRoleInGame(Role role);

	ReturnPojo updateGameState(Game game);
}

