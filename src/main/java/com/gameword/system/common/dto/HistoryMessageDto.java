package com.gameword.system.common.dto;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shawn
 * @date 2021-01-24 20:22
 **/
@Data
public class HistoryMessageDto implements Serializable {

    /*appId	String	App Key。
    fromUserId	String	发送者 Id
    targetId	String	接受者 Id，在消息路由中为 toUserId，当发送聊天室广播消息 (opens new window)、全量用户落地通知 (opens new window)（/broadcast）时此字段为空。
    targetType	Int	会话类型，二人会话是 1 、讨论组会话是 2 、群组会话是 3 、聊天室会话是 4 、客服会话是 5 、 系统通知是 6 、应用公众服务是 7 、公众服务是 8。targetType 在 SDK 中为 ConversationType。
    GroupId	String	根据不同的 targetType，可能是讨论组 Id、群组 Id 或聊天室 Id ，如 targetType 为 1 时可忽略 GroupId。
    classname	String	消息类型：文本消息 RC:TxtMsg 、 图片消息 RC:ImgMsg、GIF 消息 RC:GIFMsg、语音消息 RC:VcMsg、高质量语音消息 RC:HQVCMsg、文件消息 RC:FileMsg、小视频消息 RC:SightMsg、图文消息 RC:ImgTextMsg 、位置消息 RC:LBSMsg 、添加联系人消息 RC:ContactNtf 、提示条通知消息 RC:InfoNtf 、资料通知消息 RC:ProfileNtf 、通用命令通知消息 RC:CmdNtf。
    content	String	消息内容
    dateTime	String	消息时间
    source	String	消息来源，包括：iOS、Android、Websocket、MiniProgram（小程序）、PC、Server。
    isDiscard	String	是否被丢弃，true 为是，false 为否，只针对聊天室会话类型存在。
    isSensitiveWord	String	是否含有屏蔽敏感词，true 为含有、false 为不含有，只针对聊天室会话类型存在。
    isForbidden	String	是否为被禁言后发送的消息，只针对聊天室会话类型存在。
    isNotForward	String	消息是否不分发，true 为不分发、false 为分发，只针对聊天室会话类型存在。
    msgUID	String	可通过 msgUID 确定消息唯一。
    groupUserIds	String[]	targetType 为 3 时此参数有效，显示为群组中指定接收消息的用户 ID 数组，该条消息为群组定向消息。非定向消息时内容为空，如指定的用户不在群组中内容也为空。*/

    private String appId;
    private String fromUserId;
    private String targetId;
    private int targetType;
    private String groupId;
    private String classname;
    private String content;
    private String dateTime;
    private String source;
    private String isDiscard;
    private String isSensitiveWord;
    private String isForbidden;
    private String isNotForward;
    private String msgUID;
    private List<String> groupUserIds;
}
