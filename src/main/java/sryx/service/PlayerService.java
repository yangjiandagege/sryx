package sryx.service;

import sryx.pojo.Game;
import sryx.pojo.Player;
import sryx.pojo.ReturnPojo;
import sryx.pojo.Role;

public interface PlayerService {
	ReturnPojo updatePlayer(Player player);

	ReturnPojo getPlayerById(String playerId);
	
	ReturnPojo createGame(Game game);
	
	ReturnPojo getGameById(Integer gameId);
	
	ReturnPojo getGameByInviteCode(String inviteCode);

	ReturnPojo updateRole(Role role);

	ReturnPojo getRoleListInGame(Game game);

	ReturnPojo getMyRoleInGame(Role role);

	ReturnPojo updateGameState(Game game);
	
	ReturnPojo updateRoleDeathState(Role role);

	ReturnPojo getMyGameRecords(String playerId);
}

