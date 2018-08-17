package com.thorn.springboot.controller;

import com.thorn.springboot.dao.postMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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

    @RequestMapping("/BBS/page")
    public String Preprocess(@RequestParam(required = false, defaultValue = "1") int param,
                             @RequestParam(required = false, defaultValue = "0") int method,
                             @RequestParam(required = false, defaultValue = "") String content,
                             Model model) {


        model.addAttribute("EssencePosts", postMapper.findEssencePost());
        model.addAttribute("HotPosts", postMapper.findHotPost());
        model.addAttribute("TopPosts", postMapper.findTopPost());

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
        model.addAttribute("posts", postMapper.findPost(content, (param - 1) * 10));
        return "/BBS/index";
        //return "redirect:/BBS/turn";
    }
}
