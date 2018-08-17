package com.thorn.springboot.dao;

import com.thorn.springboot.model.reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface replyMapper {
    int insert(reply record);

    int insertSelective(reply record);

    List<reply> getReplyTop(int id, int page);

    List<reply> getReplySub(int id, int page);

    int getReplyNum(int id);

    reply getTop(int id);

    void newreply(reply reply);

    void delreply(reply reply);

    int getTheSubReplyNum(reply reply);

    void newSubReply(reply reply);

    void addlastfloor(int postid, int floor, int floorex);

    String findTheParentReplyer(reply reply);

    void delSubReply(reply reply);

    int getMaxReplyNum(int id);

    void deteleReply2(String username);

    int deleteReply(String username);
}