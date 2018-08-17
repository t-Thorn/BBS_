package com.thorn.springboot.controller;

import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.model.post;
import com.thorn.springboot.model.reply;
import com.thorn.springboot.model.userWithBLOBs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("userSession")
@MapperScan("com.thorn.springboot.dao")
public class PostController {
    @Autowired
    postMapper postmapper;
    @Autowired
    com.thorn.springboot.dao.userMapper userMapper;
    @Autowired
    com.thorn.springboot.dao.replyMapper replyMapper;

    private org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(this.getClass());

    @GetMapping("/Post/new")
    public String jumptoNewPost() {
        return "/Login/write";
    }

    @PostMapping("/Post/newpost")
    public String newpost(@ModelAttribute("newpost") post post,
                          @ModelAttribute("userSession") userWithBLOBs user,
                          @RequestParam String content, Model model
            , RedirectAttributes redirectAttributes) {
        //logger.info(post.getTitle());
        //logger.info(post.getType());
        //logger.info(content);

        post.setUsername(user.getUsername());
        post.setPosttime(new Date());
        post.setLastposttime(new Date());
        postmapper.insert(post);

        int newid = postmapper.findNewPostID();
        //logger.info(postmapper.findNewPostID());

        user.setMyPostnum(user.getMyPostnum() + 1);
        userMapper.updateMyPostnum(user);

        reply reply = new reply();
        reply.setPostid(newid);
        reply.setContent(content);
        reply.setFloor(0);
        reply.setReplyer(user.getUsername());
        reply.setReplytime(new Date());

        replyMapper.insert(reply);
        redirectAttributes.addAttribute("param", newid);
        logger.debug("newid:" + newid);
        return "redirect:/BBS/post";
    }


    @PostMapping("/Post/newreply")
    public String newReply(@ModelAttribute("newreply") reply reply, @ModelAttribute("userSession")
            userWithBLOBs user, Model model) {
        //新增回复
        reply.setReplytime(new Date());
        reply.setReplyer(user.getUsername());
        replyMapper.newreply(reply);

        //更新贴主的
        // replyMapper.addlastfloor(reply.getPostid(),0,-1);

        //更新帖子回复数
        postmapper.addPostnum(reply.getPostid());
        //传参
        //model.addAttribute("test", "test1");
        //model.addAttribute("pages",pages);
        return "redirect:/BBS/post?param=" + reply.getPostid() + "&refloor=" + replyMapper.getMaxReplyNum(
                reply.getPostid());
    }

    @PostMapping("/Post/delreply")
    public String delReply(@ModelAttribute("delreply") reply reply, @ModelAttribute("userSession")
            userWithBLOBs user, Model model) {
        //新增回复
        replyMapper.delreply(reply);

        //更新帖子回复数
        postmapper.subPostnum(reply.getPostid());
        //model.addAttribute("pages",pages);
        return "redirect:/BBS/post?param=" + reply.getPostid();
    }

    @PostMapping(value = "/Post/iscollection")
    @ResponseBody
    public Map<String, Integer> collection(int postid, int method, @ModelAttribute
            ("userSession") userWithBLOBs user, Model model) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (user == null || user.getUsername() == null)
            return map;
        //logger.info("postid:" + postid);
        String[] collections = new String[]{""};
        if (user.getCollections() != null && !user.getCollections().equals("")) {
            collections = user.getCollections().split(";");
        }
        //
        if (method == 0) {
            //查询是否收藏
            if (!collections[0].equals("")) {
                for (String collection : collections) {
                    if (collection.equals(String.valueOf(postid))) {
                        map.put("status", 1);
                        map.put("num", -1);
                        return map;
                    }
                }
                map.put("status", 0);
                map.put("num", -1);
                return map;
            }
            map.put("status", 0);
            map.put("num", -1);
            return map;
        } else {
            //收藏/取消收藏
            String newcollections = "";
            for (int index = 0; index < collections.length; index++) {
                if (collections[index].equals(String.valueOf(postid))) {
                    //取消收藏
                    newcollections = setcollection(collections, index, "");
                    //logger.info("newcollections:" + newcollections);
                    user.setCollections(newcollections);
                    userMapper.updateCollections(user);
                    postmapper.subCollectionNum(postid);
                    model.addAttribute(user);
                    map.put("status", 0);
                    map.put("num", postmapper.findThePost(postid).getCollectionnum());
                    return map;
                }
            }
            //收藏
            newcollections = setcollection(collections, -1, String.valueOf(postid));
            //logger.info("newcollections:" + newcollections);
            user.setCollections(newcollections);
            userMapper.updateCollections(user);
            postmapper.addCollectionNum(postid);
            model.addAttribute(user);
            map.put("status", 1);
            map.put("num", postmapper.findThePost(postid).getCollectionnum());
            return map;
        }
    }

    public String setcollection(String[] collections, int index, String newcollection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < collections.length; i++) {
            if (i == index)
                continue;
            if (!collections[0].equals(""))
                stringBuilder.append(collections[i] + ";");
        }
        if (!newcollection.equals(""))
            stringBuilder.append(newcollection);
        return stringBuilder.toString();
    }

    @PostMapping(value = "/Post/subreply")
    @ResponseBody
    public Map<String, Object> subreply(@RequestParam(value = "postid", defaultValue = "-1") int postid,
                                        @RequestParam(value = "floor", defaultValue = "-1") int floor,
                                        @RequestParam(value = "floorex", defaultValue = "-1") int floorex,
                                        @RequestParam(value = "content", defaultValue = "") String content,
                                        @ModelAttribute("userSession") userWithBLOBs user) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("content", "");
        if (user != null && user.getUsername() != null) {
            reply reply = new reply();
            reply.setPostid(postid);
            reply.setFloor(floor);
            if (floorex == -1) {
                //新增回复
                reply.setReplyer(user.getUsername());
                reply.setReplytime(new Date());
                reply.setContent(content);
                int nowfloorex = replyMapper.getTheSubReplyNum(reply);
                nowfloorex++;
                reply.setFloorex(nowfloorex);
                replyMapper.newSubReply(reply);
                result.put("content", reply.getContent());
                String parentReplyer = userMapper.getSimpleInfo(replyMapper.findTheParentReplyer
                        (reply)).getName();
                String replyer = userMapper.getSimpleInfo(reply.getReplyer()).getName();
                result.put("parentreplyer", parentReplyer);
                result.put("replyer", replyer);
                result.put("floorex", nowfloorex);
                //logger.info(reply.toString());
                return result;
            } else {
                //删除回复
                reply.setFloorex(floorex);
                replyMapper.delSubReply(reply);
                result.put("content", "ok");
                return result;
            }
        }
        return result;
    }
}
