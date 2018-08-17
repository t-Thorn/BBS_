package com.thorn.springboot.controller;

import com.thorn.springboot.model.userWithBLOBs;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("userSession")
@MapperScan("com.thorn.springboot.dao")
public class UserController {
    @Autowired
    com.thorn.springboot.dao.userMapper userMapper;
    private org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger(this.getClass());

    @RequestMapping("/user/user")
    public String jump() {
        return "/Login/login";
    }

    @GetMapping("/user/uploadImg")
    public String turn() {
        return "/proto2/uploadImg";
    }

    @RequestMapping("/user/out")
    public String out(SessionStatus status) {
        status.setComplete();
        return "redirect:/BBS/page";
    }

    @GetMapping("/user/OA")
    public String jumptoOA(@ModelAttribute("userSession") userWithBLOBs user) {
        if (user != null && user.getUsername() != null && user.getLevel() == 0) {
            return "/proto/index";
        }
        return "redirect:/BBS/page";
    }

    @PostMapping(value = "/user/reg")
    public String InsertUser(HttpServletRequest request, Model model) {
        List<userWithBLOBs> users;

        userWithBLOBs user = new userWithBLOBs();

        users = userMapper.findusername(request.getParameter("username"));

        if (users.size() <= 0) {
            if (request.getParameter("username").length() <= 18 && request.getParameter("password").length() <= 18) {
                if (request.getParameter("password").equals(request.getParameter("repassword"))) {
                    users = userMapper.findoneID(request.getParameter("identity"));
                    if (users.size() <= 0) {
                        user.setUsername(request.getParameter("username"));
                        user.setPassword(request.getParameter("password"));
                        user.setIdentity(request.getParameter("identity"));
                        user.setName(request.getParameter("name"));
                        user.setRegdate(new Date());
                        userMapper.InsertUser(user);
                        model.addAttribute("tip", "注册成功");
                    } else {
                        model.addAttribute("tip", "身份证重复");
                    }
                } else {
                    model.addAttribute("tip", "两次密码不相同");
                }
            } else {
                model.addAttribute("tip", "用户名和密码不能超过18位重复");
            }
        } else {
            model.addAttribute("tip", "用户名重复");
        }
        return "/Login/login";
    }

    @PostMapping(value = "/user/login", produces = "application/json")
    public String login(HttpServletRequest request, Model model) {
        userWithBLOBs user;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        user = userMapper.login(username);
        if (user == null) {
            model.addAttribute("tip", "用户名不存在");
            return "forward:/user/user";
        }
        if (password.equals(user.getPassword())) {
            model.addAttribute("userSession", user);
            if (user.getLevel() == 0) {
                return "redirect:/BBS/page";
            } else {
                return "redirect:/BBS/page";
            }
        } else {
            model.addAttribute("tip", "用户名或密码错误");
            return "forward:/user/user";
        }
    }

    @PostMapping(value = "user/update")
    public String update(HttpServletRequest request, @ModelAttribute("userSession") userWithBLOBs user,
                         Model model) {
        user.setUsername(user.getUsername());
        user.setName(request.getParameter("name"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        user.setIdentity(request.getParameter("identity"));
        user.setGender(request.getParameter("sex"));
        model.addAttribute("userSession", user);
        userMapper.updateUser(user);
        model.addAttribute("tip", "修改成功");
        return "/proto2/setting";
    }

    @PostMapping(value = "user/updatepwd")
    public String updatepwd(HttpServletRequest request, @ModelAttribute("userSession") userWithBLOBs user,
                            Model
                                    model) {
        userWithBLOBs user1 = new userWithBLOBs();
        user1.setUsername(user.getUsername());
        user1 = userMapper.login(user1.getUsername());
        if (request.getParameter("currentpwd").equals(user1.getPassword())) {
            if (request.getParameter("newpwd").equals(request.getParameter("confirmpwd"))) {
                user1.setPassword(request.getParameter("newpwd"));
                userMapper.updatePwd(user1);
                user = user1;
                model.addAttribute("userSession", user);
                model.addAttribute("tip", "修改成功");
                return "/proto2/upload_password";
            } else {
                model.addAttribute("tip", "两次输入不一致");
                return "/proto2/upload_password";
            }
        } else {
            model.addAttribute("tip", "密码错误");
            return "/proto2/upload_password";
        }
    }


    @PostMapping(value = "user/updatephoto")
    public String updatephoto(@RequestParam("file") MultipartFile file,
                              @ModelAttribute("userSession") userWithBLOBs user, Model model) throws
            IOException {
        if (!file.isEmpty()) {
            File f = new File("E:\\project\\photo\\" + user.getPhoto());
            if (f.exists()) {
                f.delete();
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

            String filename = user.getUsername() + df.format(new Date()) + ".jpg";
            file.transferTo(new File("E:\\project\\photo\\" + File.separator + filename));
            System.out.println("second");
            user.setPhoto(filename);
            userMapper.updatePhoto(user);

            model.addAttribute("userSession", user);
            model.addAttribute("tip", "修改成功");

            return "/proto2/uploadImg";
        } else {
            model.addAttribute("tip", "请选择图片");
            return "/proto2/uploadImg";
        }
    }


}
