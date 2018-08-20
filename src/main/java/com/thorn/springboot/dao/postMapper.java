package com.thorn.springboot.dao;

import com.thorn.springboot.model.post;
import com.thorn.springboot.model.reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface postMapper {
    int insert(post record);

    int insertSelective(post record);

    List<post> findPost(String content);//-1*10

    List<post> findEssencePost();

    int getPostNum(String content);

    List<post> findHotPost();

    List<post> findTopPost();

    post findThePost(int id);

    void updateViews(int id);

    int findNewPostID();

    void addCollectionNum(int id);

    void subCollectionNum(int id);

    void addPostnum(int id);

    void subPostnum(int id);

    List<post> findAllPost();

    List<post> getPostByPage(int i, int j);

    int cancel(int id);

    int stick(int id);

    int good(int id);

    int stickandgood(int id);

    int detelePost(int id);

    int deteleReply(int postid);

    int detelePost2(String username);

    List<post> findSomePost(String name);

    List<post> getPostByPageName(String name, int i, int j);

    List<post> getMyPost(String username);

    List<post> getMyPostAndPage(String username, int i, int j);

    List<post> getMyCollect(int id);

    int decCollectNum(int id);

    List<post> getMyHistory(int id);

    List<reply> getReply(String username);
}