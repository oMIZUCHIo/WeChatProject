package com.zwj.service.impl;

import com.zwj.entity.*;
import com.zwj.exception.ErrorCodeEnum;
import com.zwj.exception.MyRuntimeException;
import com.zwj.mapper.*;
import com.zwj.service.GroupChatReceiveMsgsService;
import com.zwj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

@Service("groupChatReceiveMsgsService")
public class GroupChatReceiveMsgsServiceImpl implements GroupChatReceiveMsgsService {

    @Autowired
    @Qualifier("groupChatReceiveMsgsMapper")
    private GroupChatReceiveMsgsMapper groupChatReceiveMsgsMapper;

    @Autowired
    @Qualifier("usersOfUserGroupMapper")
    private UsersOfUserGroupMapper usersOfUserGroupMapper;

    @Autowired
    @Qualifier("groupChatSendMsgsMapper")
    private GroupChatSendMsgsMapper groupChatSendMsgsMapper;

    @Autowired
    @Qualifier("contentMapper")
    private ContentMapper contentMapper;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("userGroupsMapper")
    private UserGroupsMapper userGroupsMapper;

    @Autowired
    @Qualifier("groupChatReceiveMsgs")
    private GroupChatReceiveMsgs groupChatReceiveMsgs;

    @Autowired
    @Qualifier("groupChatReceiveMsgsVo")
    private GroupChatReceiveMsgsVo groupChatReceiveMsgsVo;

    @Autowired
    @Qualifier("groupChatNoReceiveMsgs")
    private GroupChatNoReceiveMsgs groupChatNoReceiveMsgs;

    @Autowired
    @Qualifier("groupChatSendMsgs")
    private GroupChatSendMsgs groupChatSendMsgs;

    @Autowired
    @Qualifier("userGroups")
    private UserGroups userGroups;

    @Autowired
    @Qualifier("usersOfUserGroup")
    private UsersOfUserGroup usersOfUserGroup;

    @Autowired
    @Qualifier("user")
    private User user;

    @Autowired
    @Qualifier("content")
    private Content content;

    @Autowired
    private List<GroupChatReceiveMsgsVo> groupChatReceiveMsgsVoList;

    @Autowired
    private List<GroupChatNoReceiveMsgs> groupChatNoReceiveMsgsList;

    @Autowired
    private List<GroupChatSendMsgs> groupChatSendMsgsList;

    private List<Integer> msgIdList;

    private Map<String,Integer> map;

    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    public void setGroupChatReceiveMsgsMapper(GroupChatReceiveMsgsMapper groupChatReceiveMsgsMapper) {
        this.groupChatReceiveMsgsMapper = groupChatReceiveMsgsMapper;
    }

    public void setUsersOfUserGroupMapper(UsersOfUserGroupMapper usersOfUserGroupMapper) {
        this.usersOfUserGroupMapper = usersOfUserGroupMapper;
    }

    public void setGroupChatReceiveMsgsVo(GroupChatReceiveMsgsVo groupChatReceiveMsgsVo) {
        this.groupChatReceiveMsgsVo = groupChatReceiveMsgsVo;
    }

    public void setGroupChatNoReceiveMsgs(GroupChatNoReceiveMsgs groupChatNoReceiveMsgs) {
        this.groupChatNoReceiveMsgs = groupChatNoReceiveMsgs;
    }

    public void setGroupChatReceiveMsgs(GroupChatReceiveMsgs groupChatReceiveMsgs) {
        this.groupChatReceiveMsgs = groupChatReceiveMsgs;
    }

    public void setGroupChatSendMsgsMapper(GroupChatSendMsgsMapper groupChatSendMsgsMapper) {
        this.groupChatSendMsgsMapper = groupChatSendMsgsMapper;
    }

    public void setGroupChatSendMsgs(GroupChatSendMsgs groupChatSendMsgs) {
        this.groupChatSendMsgs = groupChatSendMsgs;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUserGroups(UserGroups userGroups) {
        this.userGroups = userGroups;
    }

    public void setUserGroupsMapper(UserGroupsMapper userGroupsMapper) {
        this.userGroupsMapper = userGroupsMapper;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setUsersOfUserGroup(UsersOfUserGroup usersOfUserGroup) {
        this.usersOfUserGroup = usersOfUserGroup;
    }

    public void setGroupChatReceiveMsgsVoList(List<GroupChatReceiveMsgsVo> groupChatReceiveMsgsVoList) {
        this.groupChatReceiveMsgsVoList = groupChatReceiveMsgsVoList;
    }

    public void setGroupChatSendMsgsList(List<GroupChatSendMsgs> groupChatSendMsgsList) {
        this.groupChatSendMsgsList = groupChatSendMsgsList;
    }

    //某用户获得在所有群聊中未接收到的群聊信息
    @Override
    public List<GroupChatNoReceiveMsgs> getNoReceiveGroupMsg(int userId) {

        //获得用户加入的所有群聊ID
        List<Integer> groupIdsList = usersOfUserGroupMapper.getGroupIdsList(userId);

        if(groupIdsList.size() == 0){
            return null;
        }else{

            groupChatReceiveMsgsVoList.clear();
            groupChatNoReceiveMsgsList.clear();

            //遍历每一个群聊ID
            for(Integer groupId : groupIdsList){

                groupChatReceiveMsgsVo.setUg_id(groupId);

                //根据该群聊ID获取群聊信息
                userGroups = userGroupsMapper.queryUserGroupsByUgId(groupId);

                groupChatNoReceiveMsgs = new GroupChatNoReceiveMsgs();
                groupChatReceiveMsgs = new GroupChatReceiveMsgs();

                //放入在前端展示的群聊基本信息（群聊ID，群聊名，群聊头像）
                groupChatNoReceiveMsgs.setUg_id(groupId);
                groupChatNoReceiveMsgs.setUg_name(userGroups.getUg_name());
                groupChatNoReceiveMsgs.setUg_icon(userGroups.getUg_icon());


                groupChatReceiveMsgs.setTo_user_id(userId);
                groupChatReceiveMsgs.setUg_id(groupId);
                groupChatReceiveMsgs.setStatus(2);

                //获得该群聊下对应的发送信息ID集合
                List<Integer> sendMsgIds = groupChatReceiveMsgsMapper.getGroupChatSendMsgsIds(groupChatReceiveMsgs);

                if(sendMsgIds.size() != 0){

                        //获取发送信息集合
                        groupChatSendMsgsList = groupChatSendMsgsMapper.getGroupChatSendMsg(sendMsgIds);

                        for(GroupChatSendMsgs groupChatSendMsgs : groupChatSendMsgsList){

                            groupChatReceiveMsgsVo = new GroupChatReceiveMsgsVo();

                            //获得每条群聊信息所需要的数据
                            content = contentMapper.queryContentById(groupChatSendMsgs.getContent_id());
                            groupChatReceiveMsgsVo.setContent(content.getContent());
                            groupChatReceiveMsgsVo.setContent_type(content.getContent_type());
                            groupChatReceiveMsgsVo.setFile_name(content.getFile_name());
                            groupChatReceiveMsgsVo.setFile_size(content.getFile_size());

                            groupChatReceiveMsgsVo.setFrom_user_id(groupChatSendMsgs.getFrom_user_id());
                            groupChatReceiveMsgsVo.setSend_time(groupChatSendMsgs.getSend_time());
                            groupChatReceiveMsgsVo.setFrom_user_icon(userMapper.queryIconByUserId(groupChatSendMsgs.getFrom_user_id()));

                            usersOfUserGroup = new UsersOfUserGroup();
                            usersOfUserGroup.setUser_id(groupChatSendMsgs.getFrom_user_id());
                            usersOfUserGroup.setUg_id(groupId);

                            user = userMapper.queryUserNameAndNickById(groupChatSendMsgs.getFrom_user_id());
                            groupChatReceiveMsgsVo.setFrom_user_name(user.getUser_name());

                            //前端展示用户名的优先级：群聊昵称 > 用户自设昵称 > 用户唯一名
                            String nickName = usersOfUserGroupMapper.queryNickNameInGroup(usersOfUserGroup);
                            if(nickName == null){

                                if(user.getUser_nick_name() != null){
                                    groupChatReceiveMsgsVo.setFrom_user_nick_name(user.getUser_nick_name());
                                }else{
                                    groupChatReceiveMsgsVo.setFrom_user_nick_name(user.getUser_name());
                                }

                            }else{
                                groupChatReceiveMsgsVo.setFrom_user_nick_name(nickName);
                            }

                            //将每条群聊信息逐个放入集合中
                            groupChatReceiveMsgsVoList.add(groupChatReceiveMsgsVo);
                        }

                    //放入在前端展示的群聊进阶信息（群聊内的全部信息集合，最后一条信息内容，用户未接收信息总数）
                    groupChatNoReceiveMsgs.setGroupChatReceiveMsgsVoList(groupChatReceiveMsgsVoList);
                    groupChatNoReceiveMsgs.setLastContent(groupChatReceiveMsgsVoList.get(groupChatReceiveMsgsVoList.size() - 1).getContent());
                    groupChatNoReceiveMsgs.setNoReceiveNum(groupChatReceiveMsgsVoList.size());

                    //将本群聊的所要展示的所有信息放入所有群聊的信息集合中
                    groupChatNoReceiveMsgsList.add(groupChatNoReceiveMsgs);
                }

            }
                //返回所有群聊的所有信息集合
                return groupChatNoReceiveMsgsList;
        }
    }

    //给群聊中的用户新增接收信息
    @Override
    public String addGroupReceiveMsg(int gcsmId, int groupId, List<Integer> userIdsList, Map<Integer, WebSocketSession> loginUserMap) {

        //标签
        int result = 0;

        groupChatReceiveMsgs.setSend_msg_id(gcsmId);
        groupChatReceiveMsgs.setUg_id(groupId);

        //逐个对群聊中的用户增加“接收信息”
        for (Integer userId : userIdsList) {

            groupChatReceiveMsgs.setTo_user_id(userId);

            //判断所发送用户是否在线
            if (loginUserMap.get(userId) != null) {

                groupChatReceiveMsgs.setStatus(1);

            } else {

                groupChatReceiveMsgs.setStatus(2);

            }

            //当数据库添加失败时标识加一
            if (groupChatReceiveMsgsMapper.addGroupReceiveMsg(groupChatReceiveMsgs) <= 0)
                result++;
        }

        return StringUtil.returnDefaultResult(result <= 0);
    }

    //将未读信息改为已读
    @Override
    public String groupMsgToHaveRead(int userId, int groupId) {

        map.clear();
        map.put("ug_id",groupId);
        map.put("to_user_id",userId);
        map.put("status",2);

        //获得用户在群聊中的未读信息ID集合
        msgIdList = groupChatReceiveMsgsMapper.queryStatusGroupMsgId(map);

        if(msgIdList.size() == 0){
            throw new MyRuntimeException(ErrorCodeEnum.SUCCESS);
        }else{
            return StringUtil.returnDefaultResult(groupChatReceiveMsgsMapper.updateGroupMessageType(msgIdList) > 0);
        }
    }
}
