package com.gameword.system.common.processor;

import com.alibaba.fastjson.JSON;
import com.gameword.system.common.dto.HistoryMessageDto;
import com.gameword.system.common.utils.*;
import com.gameword.system.system.model.ChatMessageModel;
import com.gameword.system.system.model.UserModel;
import com.gameword.system.system.service.IChatMessageService;
import com.gameword.system.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 历史信息处理器
 *
 * @author shawn
 * @date 2021-01-24 18:52
 **/
@Component
public class HistoryMessageProcessor {

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private RongyunUtil rongyunUtil;

    @Autowired
    private IUserService userService;

    private final static Logger logger = LoggerFactory.getLogger(HistoryMessageProcessor.class);

    @Scheduled(cron = "0 5 */1 * * ?")
    public void syncHistoryMessage() {
        String lastHourStr = DateUtil.DateToString(DateUtil.addHour(new Date(), -1), DateStyle.YYYYMMDDHH);
        String url = rongyunUtil.historyMessage(lastHourStr);

        if (StringUtils.isEmpty(url)) {
            logger.info("syncHistoryMessage, result is empty, lastHour:{}, resultUrl:{}.", lastHourStr, url);
            return;
        }

        try {
            String zipFileName = String.format("/tmp/%s.zip", lastHourStr);
            String unZipfileName = String.format("/tmp/%s.txt", lastHourStr);
            FileUtil.downloadFile(url, zipFileName);
            ZipUtil.unZipFiles(zipFileName, unZipfileName);

            FileUtil.readFile(unZipfileName, (String line) -> {
                logger.info("unzip file, line:{}", line);
                ChatMessageModel chatMessageModel = new ChatMessageModel();
                HistoryMessageDto messageDto = JSON.parseObject(line, HistoryMessageDto.class);

                int userId = Integer.valueOf(messageDto.getFromUserId());
                int chatUserId = Integer.parseInt(messageDto.getTargetId());

                chatMessageModel.setMessageType(messageDto.getTargetType());
                chatMessageModel.setChatroomId(messageDto.getGroupId());
                chatMessageModel.setChatroomName(rongyunUtil.getBusinessRoomId().equals(messageDto.getGroupId())
                        ? "商务聊天室" : "公共聊天室");
                chatMessageModel.setSensitiveWord(messageDto.getIsSensitiveWord());
                chatMessageModel.setContent(messageDto.getContent());
                chatMessageModel.setUserId(userId);
                chatMessageModel.setChatTime(DateUtil.StringToDate(messageDto.getDateTime()));
                String classname = messageDto.getClassname();
                chatMessageModel.setHasVoice(("RC:VcMsg".equals(classname) || "RC:HQVCMsg".equals(classname)) ? 1 : 0);
                chatMessageModel.setChatUserId(chatUserId);

                Map<Integer, UserModel> userMap =  userService.findUserMapByUserIds(Arrays.asList(userId, chatUserId));
                if (userMap.get(userId) != null) {
                    UserModel userModel = userMap.get(userId);
                    chatMessageModel.setUserName(userModel.getUserName());
                    chatMessageModel.setNickName(userModel.getNickName());
                }
                if (userMap.get(chatUserId) != null) {
                    UserModel chatUserModel = userMap.get(chatUserId);
                    chatMessageModel.setChatUserName(chatUserModel.getUserName());
                    chatMessageModel.setChatNickName(chatUserModel.getNickName());
                }
                chatMessageService.saveNotNull(chatMessageModel);
            });
        } catch(Exception e) {
            e.printStackTrace();
        }

        logger.info("syncHistoryMessage, lastHour:{}, resultUrl:{}.", lastHourStr, url);
    }

}
