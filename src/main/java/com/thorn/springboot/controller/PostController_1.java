package com.thorn.springboot.controller;

import com.thorn.springboot.dao.postMapper;
import com.thorn.springboot.dao.userMapper;
import com.thorn.springboot.model.post;
import com.thorn.springboot.model.reply;
import com.thorn.springboot.model.userWithBLOBs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("userSession")
@MapperScan("com.thorn.springboot.dao")
public class PostController_1 {
    @Autowired
    postMapper postMapper;
    @Autowired
    userMapper userMapper;

    @GetMapping(value = "OA/Post", produces = "application/json")
    public String intopost(String page, Model model, @RequestParam(value = "search") String search) {
        List<post> posts;
        int pageSize = 5;
        System.out.println("--------------------" + search);
        if (!search.equals("null")) {
            posts = postMapper.findSomePost("%" + search + "%");

            if (posts.size() > 0) {
                model.addAttribute("postNum", posts.size());

                //总页数
                int pageTimes;
                if (posts.size() % pageSize == 0) {
                    pageTimes = posts.size() / pageSize;
                } else {
                    pageTimes = posts.size() / pageSize + 1;
                }

                model.addAttribute("pageTimes", pageTimes);

                //页面初始的时候page没有值
                if (null == page) {
                    page = "1";
                }

                //每页开始的第几条记录
                int startRow = (Integer.parseInt(page) - 1) * pageSize;
                posts = this.postMapper.getPostByPageName("%" + search + "%", startRow, pageSize);
                System.out.println(posts.size());
                model.addAttribute("currentPage", Integer.parseInt(page));
                model.addAttribute("post", posts);
            } else {
                model.addAttribute("tip", "没有该帖子");
                posts = postMapper.findAllPost();
                //查到的总用户数
                model.addAttribute("postNum", posts.size());

                //总页数
                int pageTimes;
                if (posts.size() % pageSize == 0) {
                    pageTimes = posts.size() / pageSize;
                } else {
                    pageTimes = posts.size() / pageSize + 1;
                }

                model.addAttribute("pageTimes", pageTimes);

                //页面初始的时候page没有值
                if (null == page) {
                    page = "1";
                }

                //每页开始的第几条记录
                int startRow = (Integer.parseInt(page) - 1) * pageSize;
                posts = this.postMapper.getPostByPage(startRow, pageSize);
                model.addAttribute("currentPage", Integer.parseInt(page));
                model.addAttribute("post", posts);
                System.out.println(posts.size());
            }
        } else {
            posts = postMapper.findAllPost();
            //查到的总用户数
            model.addAttribute("postNum", posts.size());

            //总页数
            int pageTimes;
            if (posts.size() % pageSize == 0) {
                pageTimes = posts.size() / pageSize;
            } else {
                pageTimes = posts.size() / pageSize + 1;
            }

            model.addAttribute("pageTimes", pageTimes);

            //页面初始的时候page没有值
            if (null == page) {
                page = "1";
            }

            //每页开始的第几条记录
            int startRow = (Integer.parseInt(page) - 1) * pageSize;
            posts = this.postMapper.getPostByPage(startRow, pageSize);
            model.addAttribute("currentPage", Integer.parseInt(page));
            model.addAttribute("post", posts);
            System.out.println(posts.size());
            System.out.println("-----------size" + pageSize);
        }


        return "proto/newsType";

    }

    @GetMapping(value = "OA/stick")
    public String stick(int id, int grade) {
        if (grade == 0) {
            postMapper.stick(id);
        } else if (grade == 2) {
            postMapper.cancel(id);
        } else if (grade == 3) {
            postMapper.good(id);
        } else {
            postMapper.stickandgood(id);
        }
        return "redirect:/OA/Post?search=null";
    }

    @GetMapping(value = "OA/good")
    public String good(int id, int grade) {
        if (grade == 0) {
            postMapper.good(id);
        } else if (grade == 1) {
            postMapper.cancel(id);
        } else if (grade == 3) {
            postMapper.stick(id);
        } else {
            postMapper.stickandgood(id);
        }
        return "redirect:/OA/Post?search=null";
    }

    //管理员删帖
    @GetMapping(value = "OA/deletepost")
    public String deletepost(@RequestParam(value = "id") String id, String username, Model model) {
        // System.out.println(username);
        System.out.println("-----------id:" + id);
        model.addAttribute("username", username);
        postMapper.detelePost(Integer.parseInt(id));
        postMapper.deteleReply(Integer.parseInt(id));
        //删除收藏中的信息
        List<userWithBLOBs> userWithBLOBs = userMapper.findAllUsers();
        for (int i = 0; i < userWithBLOBs.size(); i++) {
            String collect = userWithBLOBs.get(i).getCollections();

            if (collect != null && !collect.equals("")) {

                String[] collects = collect.split(";");
                collect = "";
                for (int j = 0; j < collects.length; j++) {
                    if (!collects[j].equals(id)) {
                        if (j == collects.length - 1) {
                            collect += collects[j];
                        } else {
                            collect += collects[j] + ";";
                        }
                    }
                }
                userWithBLOBs.get(i).setCollections(collect);
                userMapper.updateCollections(userWithBLOBs.get(i));
            }
        }


        //删除历史纪录中的信息
        for (int i = 0; i < userWithBLOBs.size(); i++) {
            String collect = userWithBLOBs.get(i).getHistory();
            if (collect != null && !collect.equals("")) {
                String[] collects = collect.split(";");
                collect = "";
                for (int j = 0; j < collects.length; j++) {
                    if (!collects[j].equals(id)) {
                        if (j == collects.length - 1) {
                            collect += collects[j];
                        } else {
                            collect += collects[j] + ";";
                        }
                    }
                }
            }
            userWithBLOBs.get(i).setHistory(collect);
            userMapper.updateHistory(userWithBLOBs.get(i));
        }
        return "redirect:/user/postnum";
    }

    //用户删帖
    @GetMapping(value = "user/deletepost")
    public String userdeletepost(@RequestParam(value = "id") String id, String username, Model model, @ModelAttribute
            (value = "userSession") userWithBLOBs user) {
        // System.out.println(username);

        model.addAttribute("username", username);
        postMapper.detelePost(Integer.parseInt(id));
        postMapper.deteleReply(Integer.parseInt(id));

        //删除收藏中的信息
        List<userWithBLOBs> userWithBLOBs = userMapper.findAllUsers();
        for (int i = 0; i < userWithBLOBs.size(); i++) {
            String collect = userWithBLOBs.get(i).getCollections();

            if (collect != null && !collect.equals("")) {

                String[] collects = collect.split(";");
                collect = "";
                for (int j = 0; j < collects.length; j++) {
                    if (!collects[j].equals(id)) {
                        if (j == collects.length - 1) {
                            collect += collects[j];
                        } else {
                            collect += collects[j] + ";";
                        }
                    }

                }
                userWithBLOBs.get(i).setCollections(collect);
                userMapper.updateCollections(userWithBLOBs.get(i));
            }
        }


        //删除历史纪录中的信息
        for (int i = 0; i < userWithBLOBs.size(); i++) {
            String collect = userWithBLOBs.get(i).getHistory();
            if (collect != null && !collect.equals("")) {
                String[] collects = collect.split(";");
                collect = "";
                for (int j = 0; j < collects.length; j++) {
                    if (!collects[j].equals(id)) {
                        if (j == collects.length - 1) {
                            collect += collects[j];
                        } else {
                            collect += collects[j] + ";";
                        }
                    }
                }
                if (user.getUsername() == userWithBLOBs.get(i).getUsername()) {
                    user.setHistory(collect);
                }
            }

            userWithBLOBs.get(i).setHistory(collect);
            userMapper.updateHistory(userWithBLOBs.get(i));

            model.addAttribute("userSession", user);
        }
        return "redirect:/user/postnum2";
    }

    //用户发的帖子
    @GetMapping(value = "user/mypost")
    public String mypost(@ModelAttribute("userSession") userWithBLOBs user, Model model, String page) {
        List<post> posts = postMapper.getMyPost(user.getUsername());
        //List<Post> posts;
        int pageSize = 10;
        model.addAttribute("postNum", posts.size());
        System.out.println("__________________" + user.getUsername() + "-----------------");
        //总页数
        int pageTimes;
        if (posts.size() % pageSize == 0) {
            pageTimes = posts.size() / pageSize;
        } else {
            pageTimes = posts.size() / pageSize + 1;
        }

        model.addAttribute("pageTimes", pageTimes);

        //页面初始的时候page没有值
        if (null == page) {
            page = "1";
        }

        //每页开始的第几条记录
        int startRow = (Integer.parseInt(page) - 1) * pageSize;
        posts = this.postMapper.getMyPostAndPage(user.getUsername(), startRow, pageSize);
        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("post", posts);
        return "proto2/MyWrite";
    }

    //收藏的帖子
    @GetMapping(value = "user/mycollect")
    public String mycollect(@ModelAttribute("userSession") userWithBLOBs user, Model model,
                            String page) {
        List<post> posts = new ArrayList<post>();
        List<post> post;
        String collect = user.getCollections();
        if (collect == null || collect.equals("")) {
            return "proto2/myCollectNull";
        }
        String[] collects = collect.split(";");
        for (int i = 0; i < collects.length; i++) {
            post = postMapper.getMyCollect(Integer.parseInt(collects[i]));
            posts.addAll(post);
        }

        int pageSize = 10;
        model.addAttribute("postNum", posts.size());

        //总页数
        int pageTimes;
        if (posts.size() % pageSize == 0) {
            pageTimes = posts.size() / pageSize;
        } else {
            pageTimes = posts.size() / pageSize + 1;
        }

        model.addAttribute("pageTimes", pageTimes);

        //页面初始的时候page没有值
        if (null == page) {
            page = "1";
        }
        posts = new ArrayList<post>();
        //每页开始的第几条记录
        int startRow = (Integer.parseInt(page) - 1) * pageSize;
        int size = collects.length;
        if (page.equals("1")) {
            if (collects.length > 10 && collects.length <= 20) {
                int j = collects.length - 10;
                size = collects.length - j;
            }
            if (collects.length > 20) {
                int j = collects.length - 10;
                size = collects.length - j;
            }

            for (int i = 0; i < size; i++) {
                post = postMapper.getMyCollect(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        } else if (page.equals("2")) {
            if (collects.length > 20) {

                int j = collects.length - 20;
                size = collects.length - j;
            }
            for (int i = 10; i < size; i++) {
                post = postMapper.getMyCollect(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        } else if (page.equals("3")) {
            for (int i = 20; i < size; i++) {
                post = postMapper.getMyCollect(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        }

        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("post", posts);

        return "proto2/myCollect";
    }

    //取消收藏
    @GetMapping(value = "user/cancelcollect")
    public String cancel(@ModelAttribute("userSession") userWithBLOBs user, Model model, @RequestParam("id")
            String id, RedirectAttributes redirectAttributes) {
        //System.out.println("-------------------收藏减一");
        String collect = user.getCollections();
        String[] collects = collect.split(";");
        if (collect != null && !collect.equals("")) {


            collect = "";
            //System.out.println("--------------id="+id);

            for (int i = 0; i < collects.length; i++) {

                if (!collects[i].equals(id)) {
                    //System.out.println("--------------"+collects[i]);
                    if (i == collects.length - 1) {
                        collect += collects[i];
                    } else {
                        collect += collects[i] + ";";
                    }
                }

            }
        }
        System.out.println("--------------collect" + collect);
        postMapper.decCollectNum(Integer.parseInt(id));
        redirectAttributes.addAttribute("collect", collect);
        return "redirect:/user/updatecollect";
    }

    //浏览历史
    @GetMapping(value = "user/history")
    public String history(@ModelAttribute("userSession") userWithBLOBs user, Model model, String
            page) {
        List<post> posts = new ArrayList<post>();
        List<post> post;
        String collect = user.getHistory();
        if (collect == null || collect.equals("")) {
            System.out.println("------------------NULL----------");
            return "proto2/HistoryNull";
        }

        String[] collects = collect.split(";");
        for (int i = collects.length - 1; i >= 0; i--) {
            post = postMapper.getMyHistory(Integer.parseInt(collects[i]));
            posts.addAll(post);
        }

        int pageSize = 10;
        model.addAttribute("postNum", posts.size());

        //总页数
        int pageTimes;
        if (posts.size() % pageSize == 0) {
            pageTimes = posts.size() / pageSize;
        } else {
            pageTimes = posts.size() / pageSize + 1;
        }

        model.addAttribute("pageTimes", pageTimes);

        //页面初始的时候page没有值
        if (null == page) {
            page = "1";
        }
        posts = new ArrayList<post>();
        //每页开始的第几条记录
        int startRow = (Integer.parseInt(page) - 1) * pageSize;
        int size = collects.length;
        if (page.equals("1")) {
            if (collects.length > 10 && collects.length <= 20) {

                int j = collects.length - 10;
                size = collects.length - j;

            }
            if (collects.length > 20) {
                int j = collects.length - 10;
                size = collects.length - j;
            }

            for (int i = 0; i < size; i++) {
                System.out.println("-----------------1------");
                post = postMapper.getMyHistory(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        } else if (page.equals("2")) {
            if (collects.length > 20) {

                int j = collects.length - 20;
                size = collects.length - j;
            }
            for (int i = 10; i < size; i++) {
                System.out.println("-----------------2------");
                post = postMapper.getMyHistory(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        } else if (page.equals("3")) {
            for (int i = 20; i < size; i++) {
                System.out.println("-----------------3------");
                post = postMapper.getMyHistory(Integer.parseInt(collects[i]));
                posts.addAll(post);
            }
        }

        model.addAttribute("currentPage", Integer.parseInt(page));
        model.addAttribute("post", posts);

        return "proto2/History";
    }

    @GetMapping(value = "reply/get")
    public String getreply(Model model, @ModelAttribute("userSession") userWithBLOBs user) {
        List<reply> reply = new ArrayList<reply>();
        reply = postMapper.getReply(user.getUsername());
        for (int i = 0; i < reply.size(); i++) {
            if (reply.get(i).getFloorex() == -1) {
                reply.get(i).setContent("具体内容点击查看");
            }
        }
        model.addAttribute("reply", reply);
        return "proto2/myMsg";
    }
}
