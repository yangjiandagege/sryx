package sryx.utils;

import org.apache.log4j.Logger;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;

public class JPushUtils {
	protected static Logger logger = Logger.getLogger("JPush");
    private static final String JPUSH_APP_KEY = "40cf4209576fc7f9e65276c0";
    private static final String JPUSH_APP_SECRET = "1bc83fa64ac108bd320e1727";
    
    public static final int PLAYER_JOIN_GAME = 0; //玩家加入游戏
    public static final int GAME_IS_READY    = 1; //游戏准备好，所有人加入游戏
    public static final int GAME_AUTO_CANCLE = 2; //游戏自动解散
    public static final int PLAYER_IS_KILLED = 3; //玩家被杀死
    public static final int PLAYER_IS_VOTED  = 4; //玩家被投票出局
	
	public static void pushMessage(String playerId, int pushContent){
		JPushClient jpushClient = new JPushClient(JPUSH_APP_SECRET, JPUSH_APP_KEY, null, ClientConfig.getInstance());
		PushPayload payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.newBuilder()
	                        .addAudienceTarget(AudienceTarget.tag("sryx"))
	                        .addAudienceTarget(AudienceTarget.alias(playerId))
	                        .build())
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(String.valueOf(pushContent))
	                        .addExtra("from", "JPush")
	                        .build())
	                .build();
	    try {
	        PushResult result = jpushClient.sendPush(payload);
	        logger.info("Got result - " + result);
	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	    	logger.error("Connection error, should retry later", e);
	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	    	logger.error("Should review the error, and fix the request", e);
	    	logger.info("HTTP Status: " + e.getStatus());
	    	logger.info("Error Code: " + e.getErrorCode());
	    	logger.info("Error Message: " + e.getErrorMessage());
	    }
	}
}
