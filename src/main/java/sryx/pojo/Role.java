package sryx.pojo;

import java.io.Serializable;

public class Role implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Integer roleId;
    private String	playerId;
    private Integer	gameId;
    private Integer roleType;
    private String	playerNickName;
    private String	playerAvatarUrl;
    private Integer victory;
    
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getPlayerNickName() {
		return playerNickName;
	}
	public void setPlayerNickName(String playerNickName) {
		this.playerNickName = playerNickName;
	}
	public String getPlayerAvatarUrl() {
		return playerAvatarUrl;
	}
	public void setPlayerAvatarUrl(String playerAvatarUrl) {
		this.playerAvatarUrl = playerAvatarUrl;
	}
	public Integer getVictory() {
		return victory;
	}
	public void setVictory(Integer victory) {
		this.victory = victory;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", playerId=" + playerId
				+ ", gameId=" + gameId + ", roleType=" + roleType
				+ ", playerNickName=" + playerNickName + ", playerAvatarUrl="
				+ playerAvatarUrl + ", victory=" + victory + "]";
	}
    
    
}
