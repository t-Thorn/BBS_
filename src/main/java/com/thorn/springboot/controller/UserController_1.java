package com.thorn.springboot.controller;


import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.dao.replyMapper;
import com.thorn.springboot.dao.userMapper;
import com.thorn.springboot.model.userWithBLOBs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("userSession")
@MapperScan("com.thorn.springboot.dao")
public class UserController_1 {

    @Autowired
    userMapper userMapper;
    @Autowired
    postMapper postMapper;
    @Autowired
    replyMapper ReplyMapper;

    @GetMapping(value = "myinform")
    public String myinform() {
        return "proto2/base";
    }

    @GetMapping(value = "tiaozhuan")
    public String tiaozhuan(@RequestParam(value = "name") String name) {
        System.out.println("---------------跳转------------------");
        return name;
    }

    @GetMapping(value = "user/select", produces = "application/json")
    public String findAllUser(String page, Model model, @RequestParam(value = "search") String search) throws IOException {
        List<userWithBLOBs> users;
        int pageSize = 7;
        int pageTimes;
        if (!search.equals("null")) {
            users = userMapper.findsomeUser("%" + search + "%");
            System.out.println("---------user----------");

            if (users.size() > 0) {
                System.out.println("是否为空" + users.size());
                System.out.println("theonly");
                if (users.size() % pageSize == 0) {

                    pageTimes = users.size() / pageSize;
                } else {
                    pageTimes = users.size() / pageSize + 1;
                }

                model.addAttribute("pageTimes", pageTimes);

                //页面初始的时候page没有值
                if (null == page) {
                    page = "1";
                }

                //每页开始的第几条记录
                int startRow = (Integer.parseInt(page) - 1) * pageSize;
                users = userMapper.getUserByPageName("%" + search + "%", startRow, pageSize);
                model.addAttribute("currentPage", Integer.parseInt(page));
                //request.setAttribute("currentPage", Integer.parseInt(page));
                model.addAttribute("user", users);
            } else if (users.size() == 0) {
                model.addAttribute("tip", "没有该用户");
                users = userMapper.findAllUser();
                model.addAttribute("userNum", users.size());
                System.out.println("five");
                //总页数

                if (users.size() % pageSize == 0) {
                    pageTimes = users.size() / pageSize;
                } else {
                    pageTimes = users.size() / pageSize + 1;
                }

                model.addAttribute("pageTimes", pageTimes);

                //页面初始的时候page没有值
                if (null == page) {
                    page = "1";
                }

                //每页开始的第几条记录
                int startRow = (Integer.parseInt(page) - 1) * pageSize;
                users = this.userMapper.getUserByPage(startRow, pageSize);
                model.addAttribute("currentPage", Integer.parseInt(page));
                //request.setAttribute("currentPage", Integer.parseInt(page));
                model.addAttribute("user", users);
            }
        }
        //查到的总用户数
        else {

            users = userMapper.findAllUser();
            model.addAttribute("userNum", users.size());

            //总页数

            if (users.size() % pageSize == 0) {
                pageTimes = users.size() / pageSize;
            } else {
                pageTimes = users.size() / pageSize + 1;
            }

            model.addAttribute("pageTimes", pageTimes);

            //页面初始的时候page没有值
            if (null == page) {
                page = "1";
            }

            //每页开始的第几条记录
            int startRow = (Integer.parseInt(page) - 1) * pageSize;
            users = this.userMapper.getUserByPage(startRow, pageSize);
            model.addAttribute("currentPage", Integer.parseInt(page));
            //request.setAttribute("currentPage", Integer.parseInt(page));
            model.addAttribute("user", users);
        }
        return "proto/user";
    }

    @GetMapping(value = "user/delete", produces = "application/json")
    public String deleteUser(String username) {
        ReplyMapper.deleteReply(username);
        ReplyMapper.deteleReply2(username);
        int j = postMapper.detelePost2(username);
        userMapper.deleteUser(username);


        return "redirect:/user/select?search=null";
    }

    @GetMapping(value = "user/updatecollect", produces = "application/json")
    public String collect(@ModelAttribute("userSession") userWithBLOBs user, @RequestParam("collect")
            String Collect, Model model) {
        System.out.println("----------------Collect:" + Collect);
        user.setCollections(Collect);
        model.addAttribute("userSession", user);
        userMapper.updateCollect(Collect, user.getUsername());
        return "redirect:/user/mycollect";
    }

    @GetMapping(value = "user/postnum", produces = "application/json")
    public String deletepost(String username) {
        //System.out.println("user"+username);
        userMapper.updatePostnum(username);
        return "redirect:/OA/Post?search=null";
    }

    //跳转不同
    @GetMapping(value = "user/postnum2", produces = "application/json")
    public String deletepost2(String username) {
        //System.out.println("user"+username);
        userMapper.updatePostnum(username);
        return "redirect:/user/mypost";
    }
}
