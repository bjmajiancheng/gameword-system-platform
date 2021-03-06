package com.gameword.system.common.utils;

import com.alibaba.fastjson.JSON;
import io.rong.RongCloud;
import io.rong.methods.chatroom.Chatroom;
import io.rong.models.chatroom.ChatroomMember;
import io.rong.models.chatroom.ChatroomModel;
import io.rong.models.response.*;
import io.rong.models.sensitiveword.SensitiveWordModel;
import io.rong.models.user.UserModel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by majiancheng on 2020/4/26.
 */
public class RongyunUtil implements Serializable{

    private static final long serialVersionUID = 1440763827618391064L;

    private String appKey;

    private String appSecret;

    private String api;

    private String publicRoomId;

    private String businessRoomId;

    private RongCloud rongCloud;

    private final static Logger logger = LoggerFactory.getLogger(RongyunUtil.class);

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getPublicRoomId() {
        return publicRoomId;
    }

    public void setPublicRoomId(String publicRoomId) {
        this.publicRoomId = publicRoomId;
    }

    public String getBusinessRoomId() {
        return businessRoomId;
    }

    public void setBusinessRoomId(String businessRoomId) {
        this.businessRoomId = businessRoomId;
    }

    public void setRongCloud(RongCloud rongCloud) {
        this.rongCloud = rongCloud;
    }

    private RongCloud getRongCloud() {
        if(this.rongCloud == null) {
            this.rongCloud = RongCloud.getInstance(this.getAppKey(), this.getAppSecret());
        }

        return this.rongCloud;
    }

    /**
     * 注册用户信息
     *
     * @param userId
     * @param nickName
     * @param headImage
     * @return
     */
    public TokenResult register(Integer userId, String nickName, String headImage) {
        try {
            nickName = CommonUtil.defaultNickName(nickName);
            headImage = CommonUtil.defaultHeadImage(headImage);

            UserModel user = generUserModel(userId, nickName, headImage);

            return getRongCloud().user.register(user);

        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 更新用户信息
     *
     * @param userId
     * @param nickName
     * @param headImage
     * @return
     */
    public io.rong.models.Result update(Integer userId, String nickName, String headImage) {
        try {
            UserModel user = generUserModel(userId, nickName, headImage);

            return getRongCloud().user.update(user);

        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public UserResult get(Integer userId, String nickName, String headImage) {
        try {
            UserModel user = generUserModel(userId, nickName, headImage);

            return getRongCloud().user.get(user);

        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 生成融云用户信息
     *
     * @param userId
     * @param nickName
     * @param headImage
     * @return
     */
    public UserModel generUserModel(Integer userId, String nickName, String headImage) {
        return new UserModel()
                .setId(String.valueOf(userId))
                .setName(nickName)
                .setPortrait(headImage);
    }

    /**
     * 创建聊天室
     *
     * @param id
     * @param name
     * @return
     */
    public ResponseResult createChatroom(String id, String name) {
        try {
            ChatroomModel[] chatrooms = {
                    new ChatroomModel().setId(id).setName(name)
            };

            ResponseResult result = getRongCloud().chatroom.create(chatrooms);

            return result;
        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 销毁聊天室
     * @param id
     * @return
     */
    public ResponseResult destoryChatroom(String id) {
        try {
            ChatroomModel chatroomModel = new ChatroomModel()
                    .setId(id);

            ResponseResult result = getRongCloud().chatroom.destroy(chatroomModel);

            return result;
        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 获取聊天室用户信息
     * @param id
     * @return
     */
    public ChatroomUserQueryResult chatroomUsers(String id) {
        try {
            ChatroomModel chatroomModel = new ChatroomModel()
                    .setId(id)
                    .setCount(500)
                    .setOrder(1);

            ChatroomUserQueryResult chatroomQueryUserResult = getRongCloud().chatroom.get(chatroomModel);

            return chatroomQueryUserResult;
        } catch(Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 获取禁言用户信息
     *
     * @param id
     */
    public ListBlockChatroomUserResult blockUsers(String id) {
        try {
            ListBlockChatroomUserResult blockChatroomUser = getRongCloud().chatroom.block.getList(id);

            return blockChatroomUser;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 修改用户是否禁言
     *
     * @param id
     * @param userId
     * @return
     */
    public boolean updateBlockUser(String id, String userId, int blockType) {
        ChatroomMember[] members = {
                new ChatroomMember().setId(userId)
        };


        ChatroomModel chatroomModel = new ChatroomModel()
                .setId(id)
                .setMembers(members)
                .setMinute(Integer.MAX_VALUE);
        try {
            ResponseResult result = null;

            if (blockType == 1) {
                result = getRongCloud().chatroom.block.add(chatroomModel);
            } else if(blockType == 0) {
                result = getRongCloud().chatroom.block.remove(chatroomModel);
            }

            logger.info("update block user, result:{}.", result);
            return true;
        } catch(Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 校验群空间是否存在某用户
     *
     * @param id
     * @param userId
     * @return
     */
    public boolean checkChatRoomUser(String id, String userId) {
        try {
            ChatroomMember member = new ChatroomMember()
                    .setId(id)
                    .setChatroomId(userId);

            CheckChatRoomUserResult checkMemberResult = getRongCloud().chatroom.isExist(member);

            return checkMemberResult.isInChrm;
        } catch(Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 获取历史消息下载Url地址
     *
     * @return
     */
    public String historyMessage(String date) {
        try {
            HistoryMessageResult historyResult = (HistoryMessageResult) getRongCloud().message.history.get(date);

            return historyResult.getUrl();
        } catch(Exception e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public boolean addSensitiveWord(String sensitiveWord) {
        try {
            SensitiveWordModel sensitiveword = new SensitiveWordModel();
            sensitiveword.setKeyword(sensitiveWord);

            ResponseResult result = getRongCloud().sensitiveword.add(sensitiveword);
            logger.info("addSensitiveWord done, sensitiveWord:{}, result:{}", sensitiveWord, result);
            return result.getCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSensitiveWord(String sensitiveWord) {
        try {
            ResponseResult result = getRongCloud().sensitiveword.remove(sensitiveWord);
            logger.info("removeSensitiveWord done, sensitiveWord:{}, result:{}", sensitiveWord, result);
            return result.getCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        RongCloud rongCloud = RongCloud.getInstance("p5tvi9dspqek4", "YQ0XwWL6t6Mjcv");


        Chatroom chatroom = rongCloud.chatroom;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/chatroom/chatroom.html#create
         *
         * 创建聊天室
         *
         * */
        ChatroomModel[] chatrooms = {
                new ChatroomModel().setId("chatroomId1").setName("chatroomName1"),
                new ChatroomModel().setId("chatroomId2").setName("chatroomName2")
        };
        ResponseResult result = chatroom.create(chatrooms);

        System.out.println("create:  " + result.toString());

        /**
         *
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/chatroom/chatroom.html#destory
         * 销毁聊天室
         *
         * */
        ChatroomModel chatroomModel = new ChatroomModel()
                .setId("d7ec7a8b8d8546c98b0973417209a548");

        //ResponseResult chatroomDestroyResult = chatroom.destroy(chatroomModel);
        //System.out.println("destroy:  " + chatroomDestroyResult.toString());


        /**
         *
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/chatroom/chatroom.html#getMembers
         * 查询聊天室成员demo
         *
         * */

        chatroomModel = new ChatroomModel()
                .setId("chatroomId1")
                .setCount(500)
                .setOrder(1);

        ChatroomUserQueryResult chatroomQueryUserResult = chatroom.get(chatroomModel);
        System.out.println("queryUser:  " + chatroomQueryUserResult.toString());

        /**
         *
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/chatroom/chatroom.html#isExist
         * 查询聊天室成员是否存在
         *
         * */
        ChatroomMember member = new ChatroomMember()
                .setId("76894")
                .setChatroomId("76891");

        CheckChatRoomUserResult checkMemberResult = chatroom.isExist(member);
        System.out.println("checkChatroomUserResult:  " + checkMemberResult.isInChrm);

        HistoryMessageResult historyResult = (HistoryMessageResult)rongCloud.message.history.get("2021010101");
        System.out.println("historyResult: " + JSON.toJSONString(historyResult));

        SensitiveWordModel sensitiveword = new SensitiveWordModel();
        sensitiveword.setKeyword("test");
        rongCloud.sensitiveword.add(sensitiveword);
    }
}
