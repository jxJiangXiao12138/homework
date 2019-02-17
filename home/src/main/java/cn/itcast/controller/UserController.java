package cn.itcast.controller;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Auther : 32725
 * @Date: 2019/2/7 13:08
 * @Description: 处理用户操作的控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author: 32725
     * @Param: [_currentPage, _pageSize, model]
     * @Return: java.lang.String
     * @Description: 跳转到分页展示页面的方法，默认当前页为第一页，每页展示数据为5条
     **/
    @RequestMapping(value = "/find/{_currentPage}/{_pageSize}", method = RequestMethod.GET)
    public String findAllByPage(@PathVariable String _currentPage, @PathVariable String _pageSize, Model model) {
        int currentPage = 1;
        int pageSize = 5;

        //1. 处理异常情况
        if (_currentPage != null || !"".equals(_currentPage)) {
            try {
                currentPage = Integer.parseInt(_currentPage);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        if (_pageSize != null || "".equals(_pageSize)) {
            try {
                pageSize = Integer.parseInt(_pageSize);
                if (pageSize < 0) {
                    pageSize = 5;
                }
            } catch (NumberFormatException e) {
                pageSize = 5;
            }
        }
        PageBean<User> pb = userService.findAllByPage(currentPage, pageSize);
        model.addAttribute("pb", pb);
       // System.out.println(pb);
        return "list";
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public String findComplex(User user,Model model,String _currentPage,String _pageSize){
//        System.out.println(user);
        int currentPage = 1;
        int pageSize = 5;

        //1. 处理异常情况
        if (_currentPage != null || !"".equals(_currentPage)) {
            try {
                currentPage = Integer.parseInt(_currentPage);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        if (_pageSize != null || "".equals(_pageSize)) {
            try {
                pageSize = Integer.parseInt(_pageSize);
                if (pageSize < 0) {
                    pageSize = 5;
                }
            } catch (NumberFormatException e) {
                pageSize = 5;
            }
        }
        PageBean<User> pb = userService.findComplex(user,currentPage,pageSize);
        model.addAttribute("pb", pb);
        return "list";
    }


    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.lang.String
     * @Description: 点击添加用户跳转到注册页面
     **/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "add";
    }

    /**
    * @Author: 32725
    * @Param: [user]
    * @Return: java.lang.String
    * @Description: 添加用户
    **/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(User user) {
        //System.out.println(user);
        userService.addUser(user);
        return "login";
    }

    @RequestMapping(value = "/update/{id}/{currentPage}",method = RequestMethod.GET)
    public String update(@PathVariable int id,@PathVariable int currentPage,Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", currentPage);
        return "update";
    }
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public String updateUser(User user, HttpServletRequest request){
        //System.out.println(user);
        userService.updateUser(user);

        return "forward:/WEB-INF/page/login.jsp";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable int id , @RequestHeader("Referer") String referer )  {

       // System.out.println(referer);
        userService.deleteUser(id);
        return "redirect:"+referer;

    }

    @RequestMapping(value = "/delete",method =RequestMethod.DELETE )
    public String deleteSelect(HttpServletRequest request,@RequestHeader("Referer") String referer ){
        String[] ids = request.getParameterValues("choice");
        for (String id : ids) {
            userService.deleteUser(Integer.parseInt(id));
        }
       return "redirect:"+referer;
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username, String password, String checkCode, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response){
       // System.out.println(username+password+checkCode);

        //添加记住密码功能，此时登陆可以不判断验证码
        boolean flag = false;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("loginFlag".equals(cookie.getName())){
                flag=true;
            }
        }
        if (flag==false){
            //1.先判断验证码是否正确
            String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
            session.removeAttribute("CHECKCODE_SERVER");

            if (!checkCode.equalsIgnoreCase(checkcode_server)){
                //验证码不正确，直接返回登陆页面，并且打印出错原因
                model.addAttribute("login_msg", "验证码不正确");
                return "forward:/WEB-INF/page/login.jsp";
            }
        }

        //2.验证码正确，进行账号密码判断
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        User user = userService.findUserByUsernameAndPassWord(loginUser);

        if (user==null){
            //用户名或密码错误
            model.addAttribute("login_msg", "用户名或密码错误");
            return "forward:/WEB-INF/page/login.jsp";
        }
        //3.用户和密码正确
        session.setAttribute("user", user);
        Cookie loginFlag = new Cookie("loginFlag", "true");
        loginFlag.setMaxAge(60*60*24*7);
        response.addCookie(loginFlag);
        return "forward:/index.jsp";
    }
}
