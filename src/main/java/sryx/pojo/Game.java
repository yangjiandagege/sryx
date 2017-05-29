package sryx.pojo;

import java.io.Serializable;

public class Game implements Serializable{
    private static final long serialVersionUID = 1L;
    
	private Integer gameId;
	private String 	gameOwnerId;
	private String 	inviteCode;
	private Integer killerNum;
	private Integer policeNum;
	private Integer citizenNum;
	private Integer	state;
	private String	startTime;
	private String	endTime;
	private Integer result;
	
	
	public Integer getGameId() {
		return gameId;
	}


	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}


	public String getGameOwnerId() {
		return gameOwnerId;
	}


	public void setGameOwnerId(String gameOwnerId) {
		this.gameOwnerId = gameOwnerId;
	}


	public String getInviteCode() {
		return inviteCode;
	}


	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


	public Integer getKillerNum() {
		return killerNum;
	}


	public void setKillerNum(Integer killerNum) {
		this.killerNum = killerNum;
	}


	public Integer getPoliceNum() {
		return policeNum;
	}


	public void setPoliceNum(Integer policeNum) {
		this.policeNum = policeNum;
	}


	public Integer getCitizenNum() {
		return citizenNum;
	}


	public void setCitizenNum(Integer citizenNum) {
		this.citizenNum = citizenNum;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Integer getResult() {
		return result;
	}


	public void setResult(Integer result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", gameOwnerId=" + gameOwnerId
				+ ", inviteCode=" + inviteCode + ", killerNum=" + killerNum
				+ ", policeNum=" + policeNum + ", citizenNum=" + citizenNum
				+ ", state=" + state + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", result=" + result + "]";
	}
	
	
}
