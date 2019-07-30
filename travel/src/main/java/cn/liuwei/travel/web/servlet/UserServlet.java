package cn.liuwei.travel.web.servlet;

import cn.liuwei.travel.domain.ResultInfo;
import cn.liuwei.travel.domain.User;
import cn.liuwei.travel.service.UserService;
import cn.liuwei.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/userServlet/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    /*
    注册方法
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取验证码
        String check = request.getParameter("check");
        String check_verity = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //防止验证码复用
        request.getSession().removeAttribute("CHECKCODE_SERVE");
        if (check_verity == null || !check_verity.equalsIgnoreCase(check)){
//            System.out.println(check+":"+check_verity);
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        //1. 获取数据
        Map<String,String[]> params = request.getParameterMap();
        //2. 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3. 调用service完成注册

        boolean flag = userService.register(user);
        ResultInfo resultInfo = new ResultInfo();
        //4. 响应结果
        if (flag){
            //注册成功
            resultInfo.setFlag(true);

        }else {
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(),resultInfo);

    }
    /*
    登录方法
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        ResultInfo info = new ResultInfo();
        String checkCode = request.getParameter("check");
        String checkVerity = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (checkVerity == null || !checkCode.equalsIgnoreCase(checkVerity)){
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(response.getWriter(),info);
            return;
        }
        Map<String,String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        UserService userService = new UserServiceImpl();
        user = userService.login(user);
        request.getSession().setAttribute("user",user);
        if (user != null && "Y".equals(user.getStatus())){
            //登录成功
            info.setFlag(true);
            info.setData(user);

        }else if (user != null && !"Y".equals(user.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活");
        }else if(user == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(),info);
    }
    /*
    查找用户是否存在的方法
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(),user);
    }
    /*
    退出方法
     */
    public void exitUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getSession().invalidate();//销毁session
        request.removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/login.html");//跳转登录页面
    }
    /*
    激活方法
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
//           System.out.println(code);
        if (code != null){
//            UserService userService = new UserServiceImpl();
            boolean flag = userService.activeUser(code);
            String msg = null;
            if (flag){
                //激活成功
                msg = "<div style = 'text-align:center;'>激活成功，点击<a href = '"+request.getContextPath()+"login.html'>登录</a></div>";
            }else {
                //激活失败
                msg = "<div style = 'text-align:center;color=red;'>激活失败</div>";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }
}
