package com.thorn.springboot.controller;

import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.model.reply;
import com.thorn.springboot.model.userWithBLOBs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("userSession")
@MapperScan("com.thorn.springboot.dao")
public class BBSDetail {

    org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(this.getClass());
    @Autowired
    com.thorn.springboot.dao.replyMapper replyMapper;
    @Autowired
    postMapper postmapper;
    @Autowired
    com.thorn.springboot.dao.userMapper userMapper;

    @RequestMapping("/BBS/post")
    public String getdetail(@RequestParam(defaultValue = "1") int param, Model model,
                            @RequestParam(required = false, defaultValue = "1") int param1,
                            @RequestParam(value = "refloor", defaultValue = "-1", required = false)
                                    int refloor, @RequestParam(defaultValue = "1") String test) {
        //获取回复

        reply firstReply = replyMapper.getTop(param);
        model.addAttribute("firstReply", firstReply);
        int pages = replyMapper.getReplyNum(param);

        int maxfloor = 0;
        if (pages == 1)
            pages++;
        //获取最后的位置
        maxfloor = pages;
        pages--;
        int p = pages;
        pages /= 8;
        if (p % 8 != 0)
            pages++;

        //
        if (refloor != -1) {
            model.addAttribute("refloor", refloor);
            model.addAttribute("nowpage", pages);
            List<reply> top = replyMapper.getReplyTop(param, (pages - 1) * 8);
            if (top.size() > 0) {
                int lastfloor = top.get(top.size() - 1).getFloor();
                List<reply> sub = replyMapper.getReplySub(param, lastfloor);
                model.addAttribute("sub", sub);
            }
            model.addAttribute("top", top);
        } else {
            model.addAttribute("nowpage", param1);
            List<reply> top = replyMapper.getReplyTop(param, (param1 - 1) * 8);
            if (top.size() > 0) {
                int lastfloor = top.get(top.size() - 1).getFloor();
                List<reply> sub = replyMapper.getReplySub(param, lastfloor);
                model.addAttribute("sub", sub);
            }
            model.addAttribute("top", top);
        }
        model.addAttribute("pages", pages);
        return "BBS/tiezi";
    }

    @ModelAttribute
    public void Preprocess(@RequestParam(defaultValue = "1") int param, Model model,
                           @ModelAttribute("userSession") userWithBLOBs user
    ) {
        //获取帖子基本信息
        model.addAttribute("post", postmapper.findThePost(param));
        model.addAttribute("owner", userMapper.getSimpleInfo(postmapper.findThePost(param)
                .getUsername()));

        //更新帖子浏览人数
        postmapper.updateViews(param);

        if (user != null) {
            //logger.info(user.getUsername());
            if (user.getHistory() == null || user.getHistory().equals(""))
                user.setHistory(String.valueOf(param));
            else {
                String[] historys = user.getHistory().split(";");
                int iscollection = iscollection(historys, param);
                if (historys.length < 30) {
                    if (iscollection == -1) {
                        user.setHistory(user.getHistory() + ";" + param);
                    } else {
                        //已经包含这个浏览纪录,则更新
                        StringBuilder newHistorys = new StringBuilder("");
                        for (int index = 0; index < historys.length; index++) {
                            //去旧
                            if (iscollection == index)
                                continue;

                            newHistorys.append(historys[index] + ";");
                        }
                        //logger.info("historys:" + newHistorys);
                        user.setHistory(newHistorys.toString() + param);
                    }
                } else {
                    if (iscollection == -1) {
                        //不包含去除首部
                        StringBuilder newHistorys = new StringBuilder("");
                        for (int index = 1; index < historys.length; index++) {
                            newHistorys.append(historys[index] + ";");
                        }
                        user.setHistory(newHistorys.toString() + param);
                    } else {
                        //包含去旧
                        StringBuilder newHistorys = new StringBuilder("");
                        for (int index = 0; index < historys.length; index++) {
                            if (iscollection == index)
                                continue;
                            newHistorys.append(historys[index] + ";");
                        }
                        user.setHistory(newHistorys.toString() + param);
                    }
                }
            }
            userMapper.updateHistory(user);
            model.addAttribute("user", user);
        }

    }

    public int iscollection(String[] collections, int id) {
        int index = 0;
        for (index = 0; index < collections.length; index++) {
            if (collections[index].equals(String.valueOf(id))) {
                //logger.info("check");
                return index;
            }
        }
        return -1;
    }
}
