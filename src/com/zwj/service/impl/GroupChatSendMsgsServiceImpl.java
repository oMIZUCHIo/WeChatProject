package com.zwj.service.impl;

import com.zwj.entity.Content;
import com.zwj.entity.GroupChatReceiveMsgs;
import com.zwj.entity.GroupChatSendMsgs;
import com.zwj.mapper.ContentMapper;
import com.zwj.mapper.GroupChatReceiveMsgsMapper;
import com.zwj.mapper.GroupChatSendMsgsMapper;
import com.zwj.mapper.UsersOfUserGroupMapper;
import com.zwj.service.GroupChatSendMsgsService;
import com.zwj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("groupChatSendMsgsService")
public class GroupChatSendMsgsServiceImpl implements GroupChatSendMsgsService {

    @Autowired
    @Qualifier("groupChatSendMsgsMapper")
    private GroupChatSendMsgsMapper groupChatSendMsgsMapper;

    @Autowired
    @Qualifier("contentMapper")
    private ContentMapper contentMapper;

    @Autowired
    @Qualifier("usersOfUserGroupMapper")
    private UsersOfUserGroupMapper usersOfUserGroupMapper;

    @Autowired
    @Qualifier("groupChatReceiveMsgsMapper")
    private GroupChatReceiveMsgsMapper groupChatReceiveMsgsMapper;

    @Autowired
    @Qualifier("content")
    private Content contents;

    @Autowired
    @Qualifier("groupChatSendMsgs")
    private GroupChatSendMsgs groupChatSendMsgs;

    @Autowired
    @Qualifier("groupChatReceiveMsgs")
    private GroupChatReceiveMsgs groupChatReceiveMsgs;

    public void setGroupChatSendMsgsMapper(GroupChatSendMsgsMapper groupChatSendMsgsMapper) {
        this.groupChatSendMsgsMapper = groupChatSendMsgsMapper;
    }

    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    public void setContents(Content contents) {
        this.contents = contents;
    }

    public void setGroupChatSendMsgs(GroupChatSendMsgs groupChatSendMsgs) {
        this.groupChatSendMsgs = groupChatSendMsgs;
    }

    public void setUsersOfUserGroupMapper(UsersOfUserGroupMapper usersOfUserGroupMapper) {
        this.usersOfUserGroupMapper = usersOfUserGroupMapper;
    }

    public void setGroupChatReceiveMsgs(GroupChatReceiveMsgs groupChatReceiveMsgs) {
        this.groupChatReceiveMsgs = groupChatReceiveMsgs;
    }

    public void setGroupChatReceiveMsgsMapper(GroupChatReceiveMsgsMapper groupChatReceiveMsgsMapper) {
        this.groupChatReceiveMsgsMapper = groupChatReceiveMsgsMapper;
    }

    //新增群聊发送信息
    @Override
    public int addGroupSendMsg(int fromUserId, int ugId, String content, int contentType,String fileName,String fileSize) {

        contents.setContent(content);
        contents.setContent_type(contentType);
        contents.setFile_name(fileName);
        contents.setFile_size(fileSize);

        //先新增信息内容
        if (contentMapper.addContentReturnId(contents) <= 0) {
            return -1;
        } else {

            groupChatSendMsgs.setContent_id(contents.getContent_id());
            groupChatSendMsgs.setFrom_user_id(fromUserId);
            groupChatSendMsgs.setSend_time(DateUtil.getNowTime());
            groupChatSendMsgs.setUg_id(ugId);

            //在群聊信息发送表中新增发送信息
            return groupChatSendMsgsMapper.addGroupSendMsg(groupChatSendMsgs) > 0 ? groupChatSendMsgs.getGcsm_id() : -1;
        }
    }
}
