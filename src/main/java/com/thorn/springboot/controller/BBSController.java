package com.thorn.springboot.controller;

import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.model.post;
import com.thorn.springboot.util.serizlize;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("trueContent")
@MapperScan("com.thorn.springboot.dao")
public class BBSController {
    @Autowired
    postMapper postMapper;
    org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(this.getClass());

    @GetMapping("/")
    public String hello() {
        return "forward:/BBS/page";
    }

    @ModelAttribute
    public void preprocess(Model model) {
        try {
            RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
            StatefulRedisConnection connection = redisClient.connect();
            RedisCommands<String, String> commands = connection.sync();
            List<post> EssencePosts = new ArrayList<post>();//精品帖
            List<post> HotPosts = new ArrayList<post>();//热帖
            List<post> TopPosts = new ArrayList<post>();//置顶帖
            serizlize serizlize = new serizlize();
            if (commands.exists("EssencePosts") == 1) {
                EssencePosts = (List<post>) serizlize.unserizlize(commands.get("EssencePosts")
                        .getBytes("ISO-8859-1"));//获得缓存的精品贴
                HotPosts = (List<post>) serizlize.unserizlize(commands.get("HotPosts")
                        .getBytes("ISO-8859-1"));//获得缓存的热贴
                TopPosts = (List<post>) serizlize.unserizlize(commands.get("TopPosts")
                        .getBytes("ISO-8859-1"));//获得缓存的置顶贴
                model.addAttribute("EssencePosts", EssencePosts);
                model.addAttribute("HotPosts", HotPosts);
                model.addAttribute("TopPosts", TopPosts);
            } else {
                EssencePosts = postMapper.findEssencePost();//精品帖
                HotPosts = postMapper.findHotPost();//热帖
                TopPosts = postMapper.findHotPost();//置顶帖

                model.addAttribute("EssencePosts", EssencePosts);
                commands.set("EssencePosts", new String(serizlize.serialize(EssencePosts),
                        "ISO-8859-1"));//设置精品贴到redis
                commands.expire("EssencePosts", 1800);//30分钟过期

                model.addAttribute("HotPosts", HotPosts);
                commands.set("HotPosts", new String(serizlize.serialize(HotPosts),
                        "ISO-8859-1"));//设置热贴到redis
                commands.expire("HotPosts", 1800);//30分钟过期

                model.addAttribute("TopPosts", TopPosts);
                commands.set("TopPosts", new String(serizlize.serialize(TopPosts),
                        "ISO-8859-1"));//设置置顶贴到redis
                commands.expire("TopPosts", 1800);//30分钟过期
            }
            connection.close();
            redisClient.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       /* model.addAttribute("EssencePosts", postMapper.findEssencePost());
        model.addAttribute("HotPosts", postMapper.findHotPost());
        model.addAttribute("TopPosts", postMapper.findTopPost());*/
    }

    @RequestMapping("/BBS/page")
    public String posts(@RequestParam(required = false, defaultValue = "1") int param,
                        @RequestParam(required = false, defaultValue = "0") int method,
                        @RequestParam(required = false, defaultValue = "") String content,
                        Model model) {
        try {
            //创建redis
            RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
            StatefulRedisConnection connection = redisClient.connect();
            RedisCommands<String, String> commands = connection.sync();

            List<post> posts = new ArrayList<post>();


            if (param < 1)
                param = 1;

            if (method == 0) {
                Map<String, Object> map = model.asMap();
                content = (String) map.get("trueContent");
                //不然会出bug
                if (content == null)
                    content = "";
                //logger.warn(content);
            }
            int pages = 0;
            pages = postMapper.getPostNum(content);

            if (pages % 10 != 0) {
                pages = pages / 10;
                pages++;
            } else
                pages = pages / 10;

            if (pages == 0) {
                param = 0;
                model.addAttribute("searchError", "无结果");
            } else if (pages < param) {
                model.addAttribute("searchError", "页面超出范围");
                param = pages;
            }
            model.addAttribute("param", param);
            model.addAttribute("nowpage", param);
            model.addAttribute("pages", pages);
            model.addAttribute("content", content);
            model.addAttribute("trueContent", content);
            if (pages == 0) {
                return "/BBS/index";
            }

            if (!content.equals("")) {
                posts = postMapper.findPost(content);
                posts = posts.subList((param - 1) * 10, posts.size() > param * 10 ? param * 10 : posts.size());
                model.addAttribute("posts", posts);
            } else {
                if (commands.exists("posts") == 0) {
                    commands.set("posts",
                            new String(serizlize.serialize(postMapper.findPost("")),
                                    "ISO-8859-1"));
                    commands.expire("posts", 1800);
                }
                posts = (List<post>) serizlize.unserizlize(commands.get("posts").
                        getBytes("ISO-8859-1"));//获得缓存
                posts = posts.subList((param - 1) * 10, posts.size() > param * 10 ? param * 10 : posts.size());
                //model.addAttribute("posts", postMapper.findPost(content, (param - 1) * 10));
                model.addAttribute("posts", posts);
            }
            connection.close();
            redisClient.shutdown();
        } catch (Exception e) {

        }
        return "/BBS/index";
        //return "redirect:/BBS/turn";
    }
}
