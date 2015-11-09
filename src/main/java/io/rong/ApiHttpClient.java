package io.rong;

import io.rong.models.ChatroomInfo;
import io.rong.models.FormatType;
import io.rong.models.GroupInfo;
import io.rong.models.Message;
import io.rong.models.SdkHttpResult;
import io.rong.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

public class ApiHttpClient {

	private static final String RONGCLOUDURI = "http://api.cn.ronghub.com";
	
	private static final String UTF8 = "UTF-8";

    private static final String APPKEY = "0vnjpoadnwjfz";
    private static final String APPSECRET = "RbLfNztF73";

    //正式
//    private static final String APPKEY = "e5t4ouvptf40a";
//    private static final String APPSECRET = "Q3obJczVbDY";

    // 获取token
	public static SdkHttpResult getToken(String userId, String userName, String portraitUri) throws Exception {

		HttpURLConnection conn = HttpUtil
				.CreatePostHttpConnection(APPKEY, APPSECRET, RONGCLOUDURI
						+ "/user/getToken." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&name=").append(URLEncoder.encode(userName==null?"":userName, UTF8));
		sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri==null?"":portraitUri, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 检查用户在线状态
	public static SdkHttpResult checkOnline(String userId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/checkOnline." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 刷新用户信息
	public static SdkHttpResult refreshUser(String userId, String userName, String portraitUri) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/refresh." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		if (userName != null) {
			sb.append("&name=").append(URLEncoder.encode(userName, UTF8));
		}
		if (portraitUri != null) {
			sb.append("&portraitUri=").append(
					URLEncoder.encode(portraitUri, UTF8));
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 封禁用户
	public static SdkHttpResult blockUser(String userId, int minute) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/block." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&minute=").append(
				URLEncoder.encode(String.valueOf(minute), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 解禁用户
	public static SdkHttpResult unblockUser(String userId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/unblock." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 获取被封禁用户
	public static SdkHttpResult queryBlockUsers() throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/block/query." + FormatType.json.toString());

		return HttpUtil.returnResult(conn);
	}

    // 添加用户到黑名单
	public static SdkHttpResult blackUser(String userId, List<String> blackUserIds)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/blacklist/add." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		if (blackUserIds != null) {
			for (String blackId : blackUserIds) {
				sb.append("&blackUserId=").append(
						URLEncoder.encode(blackId, UTF8));
			}
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 从黑名单移除用户
	public static SdkHttpResult unblackUser(String userId, List<String> blackUserIds)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/blacklist/remove." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		if (blackUserIds != null) {
			for (String blackId : blackUserIds) {
				sb.append("&blackUserId=").append(
						URLEncoder.encode(blackId, UTF8));
			}
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 获取黑名单用户
	public static SdkHttpResult QueryblackUser(String userId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/user/blacklist/query." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 创建群
	public static SdkHttpResult createGroup(List<String> userIds, String groupId, String groupName) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/create." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, UTF8));
		sb.append("&groupName=").append(URLEncoder.encode(groupName==null?"":groupName, UTF8));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, UTF8));
			}
		}
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 加入群
	public static SdkHttpResult joinGroup(String userId, String groupId, String groupName)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/join." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, UTF8));
		sb.append("&groupName=").append(URLEncoder.encode(groupName==null?"":groupName, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 批量加入群
	public static SdkHttpResult joinGroupBatch(List<String> userIds, String groupId, String groupName) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/join." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, UTF8));
		sb.append("&groupName=").append(URLEncoder.encode(groupName==null?"":groupName, UTF8));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, UTF8));
			}
		}
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 退出群
	public static SdkHttpResult quitGroup(String userId, String groupId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/quit." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 批量退出群
	public static SdkHttpResult quitGroupBatch(List<String> userIds, String groupId)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/quit." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, UTF8));
		if (userIds != null) {
			for (String id : userIds) {
				sb.append("&userId=").append(URLEncoder.encode(id, UTF8));
			}
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 解散群
	public static SdkHttpResult dismissGroup(String userId, String groupId) throws Exception {

		HttpURLConnection conn = HttpUtil
				.CreatePostHttpConnection(APPKEY, APPSECRET, RONGCLOUDURI
						+ "/group/dismiss." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&groupId=").append(URLEncoder.encode(groupId, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 同步用户群信息
	public static SdkHttpResult syncGroup(String userId, List<GroupInfo> groups)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/sync." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		if (groups != null) {
			for (GroupInfo info : groups) {
				if (info != null) {
					sb.append(
							String.format("&group[%s]=",
									URLEncoder.encode(info.getId(), UTF8)))
							.append(URLEncoder.encode(info.getName(), UTF8));
				}
			}
		}
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 刷新群信息
	public static SdkHttpResult refreshGroupInfo(String groupId, String groupName) throws Exception {

		HttpURLConnection conn = HttpUtil
				.CreatePostHttpConnection(APPKEY, APPSECRET, RONGCLOUDURI
						+ "/group/refresh." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(groupId, UTF8));
		sb.append("&groupName=").append(URLEncoder.encode(groupName==null?"":groupName, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 刷新群信息
	public static SdkHttpResult refreshGroupInfo(GroupInfo group) throws Exception {

		HttpURLConnection conn = HttpUtil
				.CreatePostHttpConnection(APPKEY, APPSECRET, RONGCLOUDURI
						+ "/group/refresh." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(URLEncoder.encode(group.getId(), UTF8));
		sb.append("&groupName=").append(
				URLEncoder.encode(group.getName(), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 发送消息
	public static SdkHttpResult publishMessage(String fromUserId, List<String> toUserIds, Message msg) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/private/publish." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		if (toUserIds != null) {
            for (String toUserId : toUserIds) {
                sb.append("&toUserId=").append(
                        URLEncoder.encode(toUserId, UTF8));
            }
		}
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 发送消息
	public static SdkHttpResult publishMessage(String fromUserId, List<String> toUserIds,
        Message msg, String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/publish." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		if (toUserIds != null) {
            for (String toUserId : toUserIds) {
                sb.append("&toUserId=").append(
                        URLEncoder.encode(toUserId, UTF8));
            }
		}
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));

		if (pushContent != null) {
			sb.append("&pushContent=").append(
					URLEncoder.encode(pushContent, UTF8));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, UTF8));
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 发送系统消息
	public static SdkHttpResult publishSystemMessage(String fromUserId,
            List<String> toUserIds, Message msg, String pushContent, String pushData)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/system/publish." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		if (toUserIds != null) {
            for (String toUserId : toUserIds) {
                sb.append("&toUserId=").append(
                        URLEncoder.encode(toUserId, UTF8));
            }
		}
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));

		if (pushContent != null) {
			sb.append("&pushContent=").append(
					URLEncoder.encode(pushContent, UTF8));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, UTF8));
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 发送群消息
	public static SdkHttpResult publishGroupMessage(String fromUserId,
            List<String> toGroupIds, Message msg, String pushContent, String pushData)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/group/publish." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		if (toGroupIds != null) {
            for (String toGroupId : toGroupIds) {
                sb.append("&toGroupId=").append(
                        URLEncoder.encode(toGroupId, UTF8));
            }
		}
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));

		if (pushContent != null) {
			sb.append("&pushContent=").append(
					URLEncoder.encode(pushContent, UTF8));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, UTF8));
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 发送聊天室消息
	public static SdkHttpResult publishChatroomMessage(String fromUserId, List<String> toChatroomIds, Message msg) throws Exception {

		HttpURLConnection conn = HttpUtil
				.CreatePostHttpConnection(APPKEY, APPSECRET, RONGCLOUDURI
						+ "/message/chatroom/publish." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		if (toChatroomIds != null) {
            for (String toChatroomId : toChatroomIds) {
                sb.append("&toChatroomId=").append(
                        URLEncoder.encode(toChatroomId, UTF8));
            }
		}
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}
	
	public static SdkHttpResult broadcastMessage(String fromUserId, Message msg,String pushContent, String pushData) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/broadcast." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));		
		sb.append("&objectName=")
				.append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));
		if (pushContent != null) {
			sb.append("&pushContent=").append(
					URLEncoder.encode(pushContent, UTF8));
		}

		if (pushData != null) {
			sb.append("&pushData=").append(URLEncoder.encode(pushData, UTF8));
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 创建聊天室
	public static SdkHttpResult createChatroom(List<ChatroomInfo> chatrooms) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/chatroom/create." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatrooms != null) {
			for (ChatroomInfo info : chatrooms) {
				if (info != null) {
					sb.append(
							String.format("&chatroom[%s]=",
									URLEncoder.encode(info.getId(), UTF8)))
							.append(URLEncoder.encode(info.getName(), UTF8));
				}
			}
		}
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 销毁聊天室
	public static SdkHttpResult destroyChatroom(List<String> chatroomIds)
			throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/chatroom/destroy." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatroomIds != null) {
			for (String id : chatroomIds) {
				sb.append("&chatroomId=").append(URLEncoder.encode(id, UTF8));
			}
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 查询聊天室信息
	public static SdkHttpResult queryChatroom(List<String> chatroomIds) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/chatroom/query." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		if (chatroomIds != null) {
			for (String id : chatroomIds) {
				sb.append("&chatroomId=").append(URLEncoder.encode(id, UTF8));
			}
		}

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 获取消息历史记录下载地址
	public static SdkHttpResult getMessageHistoryUrl(String date) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/history." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("date=").append(URLEncoder.encode(date, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 删除消息历史记录
	public static SdkHttpResult deleteMessageHistory(String date) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/message/history/delete." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("date=").append(URLEncoder.encode(date, UTF8));
		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 获取群内成员
	public static SdkHttpResult queryGroupUserList(String groupId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/user/query." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(
				URLEncoder.encode(groupId == null ? "" : groupId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 添加禁言群成员
	public static SdkHttpResult groupUserGagAdd(String groupId, String userId, long minute) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/user/gag/add." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(
				URLEncoder.encode(groupId == null ? "" : groupId, UTF8));
		sb.append("userId=").append(
				URLEncoder.encode(userId == null ? "" : userId, UTF8));
		sb.append("minute=").append(
				URLEncoder.encode(String.valueOf(minute), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 移除禁言群成员
	public static SdkHttpResult groupUserGagRollback(String groupId, String userId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/user/gag/rollback." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(
				URLEncoder.encode(groupId == null ? "" : groupId, UTF8));
		sb.append("userId=").append(
				URLEncoder.encode(userId == null ? "" : userId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 查询被禁言群成员
	public static SdkHttpResult groupUserGagList(String groupId) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/group/user/gag/list." + FormatType.json.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("groupId=").append(
				URLEncoder.encode(groupId == null ? "" : groupId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 添加敏感词
	public static SdkHttpResult wordFilterAdd(String word) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/wordfilter/add." + FormatType.json.toString());

		if (word == null || word.length() == 0) {
			throw new Exception("word is not null or empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("word=").append(
				URLEncoder.encode(word, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 移除敏感词
	public static SdkHttpResult wordFilterDelete(String word) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/wordfilter/delete." + FormatType.json.toString());

		if (word == null || word.length() == 0) {
			throw new Exception("word is not null or empty.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("word=").append(
				URLEncoder.encode(word, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}

    // 查询敏感词列表
	public static SdkHttpResult wordFilterList() throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(APPKEY,
                APPSECRET, RONGCLOUDURI + "/wordfilter/list." + FormatType.json.toString());
		StringBuilder sb = new StringBuilder();
		sb.append("1=1");
		HttpUtil.setBodyParameter(sb, conn);
		return HttpUtil.returnResult(conn);
	}
}
